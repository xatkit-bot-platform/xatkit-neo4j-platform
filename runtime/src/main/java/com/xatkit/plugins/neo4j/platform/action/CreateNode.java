package edu.uoc.som.jarvis.neo4j.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import edu.uoc.som.jarvis.neo4j.platform.Neo4jPlatform;
import fr.inria.atlanmod.commons.log.Log;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import static java.util.Objects.isNull;

/**
 * Creates a new node with the provided {@code label}.
 * <p>
 * This class relies on the {@link Neo4jPlatform}'s {@link org.neo4j.graphdb.GraphDatabaseService} to create and
 * store the node in the associated Neo4j database.
 */
public class CreateNode extends RuntimeAction<Neo4jPlatform> {

    /**
     * The label of the node to create.
     */
    private String label;

    /**
     * Constructs a new {@link CreateNode} action with the provided {@code runtimePlatform}, {@code session}, and
     * {@code label}.
     *
     * @param runtimePlatform the {@link Neo4jPlatform} containing the database to create a node in
     * @param session         the {@link XatkitSession} associated to this action
     * @param label           the label to assign to the node
     */
    public CreateNode(Neo4jPlatform runtimePlatform, XatkitSession session, String label) {
        super(runtimePlatform, session);
        this.label = label;
    }

    /**
     * Creates a new node with the provided {@code label} and store it in the {@link Neo4jPlatform}'s Neo4j database.
     * <p>
     * This action opens a new transaction to create the node and closes it with a success code once the node has been
     * created.
     * <p>
     * <b>Note:</b> if the provided {@code label} is {@code null} a node without label is created.
     *
     * @return the created {@link Node}
     */
    @Override
    protected Object compute() {
        GraphDatabaseService dbService = this.runtimePlatform.getDbService();
        /*
         * Start a new transaction to create the node.
         */
        Transaction tx = dbService.beginTx();
        Node node;
        if(isNull(this.label)) {
            node = dbService.createNode();
        } else {
            node = dbService.createNode(Label.label(this.label));
        }
        Log.info("{0} created node {1}", this.getClass().getSimpleName(), node);
        /*
         * Closes the transaction: each action is executed in an individual thread and cannot reuse other action's
         * transactions.
         */
        tx.success();
        tx.close();
        return node;
    }
}
