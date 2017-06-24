/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package parsing.xml.maps;

import parsing.model.Node;
import parsing.model.Way;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/* FrameDemo.java requires no other files. */
public class FrameDemo {
    private static final int MAXSIZE = 1000;

    private final List<Way> goodWays;
    private final Map<Long, Node> nodes;
    private Map<Long, CanvasPoint> points;

    private Double maxLat = null, minLat = null, maxLng = null, minLng = null;
    private int height, width;

    public FrameDemo(List<Way> goodWays, Map<Long, Node> nodes) {
        this.goodWays = goodWays;
        this.nodes = nodes;
        this.points = new HashMap<>();
    }

    public void showResult() {
        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JScrollPane jScrollPane = new JScrollPane(panel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(jScrollPane);

        int indexX = 0, indexY = 0;

        GridBagConstraints c = new GridBagConstraints();
        c.anchor=GridBagConstraints.WEST;
        c.gridx = indexX;//set the x location of the grid for the next component
        c.gridy = indexY;//set the y location of the grid for the next component
        for (Way way : goodWays){
            indexX = 0;
            c.gridx = indexX;
            JLabel space = new JLabel("===================");
            panel.add(space, c);
            indexY++;
            c.gridy = indexY;
            JLabel waylabel = new JLabel(way.toString());
            panel.add(waylabel, c);
            indexX = 1;
            c.gridx = indexX;

            for (long id : way.getNodeIds()){
                JLabel nodeLabel = new JLabel(nodes.get(id).toString());
                panel.add(nodeLabel, c);
                indexY++;
                c.gridy = indexY;
            }
        }

        frame.pack();
        frame.setVisible(true);
    }


    public void showMap() {
        findExtrems();
        calculateMapSize();
        transofrmToPoints();
        drawPoints();
    }

    private void drawPoints() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new MapPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    private void transofrmToPoints() {
        points = new HashMap<>();
        for (Map.Entry<Long, Node> entry: nodes.entrySet()) {
            points.put(entry.getKey(), new CanvasPoint(entry.getValue()));
        }
    }

    private void calculateMapSize() {
        double width = Math.abs(maxLng - minLng);
        double height = Math.abs(maxLat - minLat);
        if (width > height) {
            this.width = MAXSIZE;
            this.height = calculate(height, width);
        } else {
            this.height = MAXSIZE;
            this.width = calculate(width, height);
        }
    }

    private int calculate(double first, double second) {
        return (int) ((first / second) * MAXSIZE);
    }

    private void findExtrems() {
        for (Node node: nodes.values()){
            minLat = checkLower(node.getLat(), minLat);
            minLng = checkLower(node.getLon(), minLng);
            maxLng = checkHigher(node.getLon(), maxLng);
            maxLat = checkHigher(node.getLat(), maxLat);
        }
    }

    private double checkLower(Double nodeValue, Double lowest){
        if (lowest == null || nodeValue < lowest) {
            return nodeValue;
        }
        return lowest;
    }

    private double checkHigher(Double nodeValue, Double higher){
        if (higher == null || nodeValue > higher) {
            return nodeValue;
        }
        return higher;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.

        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JScrollPane jScrollPane = new JScrollPane(panel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(jScrollPane);

        //Pridaj lable
        JLabel emptyLabelA = new JLabel("a");
        emptyLabelA.setPreferredSize(new Dimension(175, 100));
        JLabel emptyLabelB = new JLabel("b");
        emptyLabelB.setPreferredSize(new Dimension(175, 100));


        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;//set the x location of the grid for the next component
        c.gridy = 0;//set the y location of the grid for the next component

        panel.add(emptyLabelA, c);
        c.gridy = 1;
        panel.add(emptyLabelB, c);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    class CanvasPoint {
        private int x, y;
        private long id;

        CanvasPoint(Node node) {
            this.x = calculateLngToX(node.getLon());
            this.y = calculateLatToY(node.getLat());
            this.id = node.getId();
        }

        private int calculateLatToY(double lat) {
            double l = maxLat - minLat;
            double d = lat - minLat;
            double dl = d / l;
            return (int) (dl * height);
        }

        private int calculateLngToX(double lng) {
            double l = maxLng - minLng;
            double d = lng - minLng;
            double dl = d / l;
            return (int) (dl * width);
        }
    }

    class MapPane extends JPanel{

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(width, height);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.BLACK);
            for (Way way: goodWays) {
                List<Long> nodes = way.getNodeIds();
                CanvasPoint first = points.get(nodes.get(0));
                for (int index = 1; index < nodes.size(); index++){
                    CanvasPoint second = points.get(nodes.get(index));
                    g.drawLine(first.x, first.y, second.x, second.y);
                    first = second;
                }
            }
        }
    }
}


