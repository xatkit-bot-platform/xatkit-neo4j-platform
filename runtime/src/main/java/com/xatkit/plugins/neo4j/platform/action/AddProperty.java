package com.xatkit.plugins.neo4j.platform.action;

import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.neo4j.platform.Neo4jPlatform;
import fr.inria.atlanmod.commons.log.Log;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * Adds a property to the provided {@code node}.
 * <p>
 * This class relies on the {@link Neo4jPlatform}'s {@link org.neo4j.graphdb.GraphDatabaseService} to create and
 * store the property in the associated Neo4j database.
 */
public class AddProperty extends RuntimeAction<Neo4jPlatform> {

    /**
     * The {@link Node} to add a property to.
     */
    private Node node;

    /**
     * The key of the property to add.
     */
    private String key;

    /**
     * The value of the property to add.
     */
    private String value;

    /**
     * Constructs a new {@link AddProperty} action with the provided {@code runtimePlatform}, {@code session}, {@code
     * node}, {@code key}, and {@code value}.
     *
     * @param runtimePlatform the {@link Neo4jPlatform} containing the database to store the created property
     * @param session         the {@link XatkitSession} associated to this action
     * @param node            the node to add a property to
     * @param key             the key of the property to add
     * @param value           the value of the property to add
     * @throws NullPointerException if the provided {@code node} or {@code key} is {@code null}
     */
    public AddProperty(Neo4jPlatform runtimePlatform, XatkitSession session, Node node, String key, String value) {
        super(runtimePlatform, session);
        checkNotNull(node, "Cannot construct a %s action with the provided node %s", this.getClass().getSimpleName(),
                node);
        checkNotNull(key, "Cannot construct a %s action with the provided key %s", this.getClass().getSimpleName(),
                key);
        this.node = node;
        this.key = key;
        this.value = value;
    }

    /**
     * Creates a property with the provided {@code key} and {@code value} and store it in the provided {@code node}.
     * <p>
     * This action opens a new transaction to create the property and closes it with a success code once the property
     * has been created.
     *
     * @return the updated {@link Node}
     */
    @Override
    protected Object compute() {
        GraphDatabaseService dbService = this.runtimePlatform.getDbService();
        /*
         * Start a new transaction to create the node.
         */
        Transaction tx = dbService.beginTx();
        node.setProperty(key, value);
        Log.info("{0} set property {0} ({1}) in node {2}", this.getClass().getSimpleName(), key, value, node);
        /*
         * Closes the transaction: each action is executed in an individual thread and cannot reuse other action's
         * transactions.
         */
        tx.success();
        tx.close();
        return node;
    }
}
