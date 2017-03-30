package parsing.model;

import java.util.List;

/**
 * Created by dusanmatejka on 3/30/17.
 */
public class Way {
    private long id;
    private List<Long> nodeIds;

    public Way(long id, List<Long> nodeIds) {
        this.id = id;
        this.nodeIds = nodeIds;
    }

    public long getId() {
        return id;
    }

    public List<Long> getNodeIds() {
        return nodeIds;
    }

    @Override
    public String toString() {
        return "Way{" +
                "id=" + id +
                ", nodeIds=" + nodeIds +
                '}';
    }
}
