package edu.uoc.som.jarvis.neo4j.platform.action;

import edu.uoc.som.jarvis.core.platform.action.RuntimeAction;
import edu.uoc.som.jarvis.core.session.JarvisSession;
import edu.uoc.som.jarvis.neo4j.platform.Neo4jPlatform;
import fr.inria.atlanmod.commons.log.Log;
import org.neo4j.graphdb.*;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * Creates a new relationship between the provided {@code nodes}.
 * <p>
 * This class relies on the {@link Neo4jPlatform}'s {@link org.neo4j.graphdb.GraphDatabaseService} to create and
 * store the relationship in the associated Neo4j database.
 */
public class CreateRelationship extends RuntimeAction<Neo4jPlatform> {

    /**
     * The {@link Node} representing the tail of the relationship to create.
     */
    private Node fromNode;

    /**
     * The {@link Node} representing the head of the relationship to create.
     */
    private Node toNode;

    /**
     * The label of the relationship to create.
     */
    private String relationshipLabel;

    /**
     * Constructs a new {@link CreateRelationship} action with the provided {@code runtimePlatform}, {@code session},
     * {@code fromNode}, {@code toNode}, and {@code relationshipLabel}.
     *
     * @param runtimePlatform   the {@link Neo4jPlatform} containing the database to store the created relationship
     * @param session           the {@link JarvisSession} associated to this action
     * @param fromNode          the {@link Node} representing the tail of the relationship to create
     * @param toNode            the {@link Node} representing the head of the relationship to create
     * @param relationshipLabel the label of the relationship to create
     * @throws NullPointerException if the provided {@code fromNode}, {@code toNode}, or {@code relationshipLabel} is
     *                              {@code null}
     */
    public CreateRelationship(Neo4jPlatform runtimePlatform, JarvisSession session, Node fromNode, Node toNode,
                              String relationshipLabel) {
        super(runtimePlatform, session);
        checkNotNull(fromNode, "Cannot construct a %s action with the provided from node %s",
                this.getClass().getSimpleName(), fromNode);
        checkNotNull(toNode, "Cannot construct a %s action with the provided to node %s",
                this.getClass().getSimpleName(), toNode);
        checkNotNull(relationshipLabel, "Cannot construct a %s action with the provided relationship label %s",
                this.getClass().getSimpleName(), relationshipLabel);
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.relationshipLabel = relationshipLabel;
    }

    /**
     * Creates a relationship between the provided {@code fromNode} and {@code toNode} with the given {@code
     * relationshipLabel}.
     * <p>
     * This action opens a new transaction to create the relationship and closes it with a success code once the
     * relationship has been created.
     *
     * @return the created {@link Relationship}
     */
    @Override
    protected Object compute() {
        GraphDatabaseService dbService = this.runtimePlatform.getDbService();
        /*
         * Start a new transaction to create the relationship.
         */
        Transaction tx = dbService.beginTx();
        Relationship relationship = fromNode.createRelationshipTo(toNode, RelationshipType.withName(relationshipLabel));
        Log.info("{0} created relationship {0}", this.getClass().getSimpleName(), relationship);
        /*
         * Closes the transaction: each action is executed in an individual thread and cannot reuse other action's
         * transactions.
         */
        tx.success();
        tx.close();
        return relationship;
    }
}
