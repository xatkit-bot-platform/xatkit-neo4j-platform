package com.xatkit.plugins.neo4j.platform;

import com.xatkit.core.XatkitCore;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.plugins.neo4j.platform.action.AddProperty;
import com.xatkit.plugins.neo4j.platform.action.CreateNode;
import com.xatkit.plugins.neo4j.platform.action.CreateRelationship;
import org.apache.commons.configuration2.Configuration;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;

import static fr.inria.atlanmod.commons.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

/**
 * Manages a connection to a Neo4j database.
 * <p>
 * This platform can be used in execution models by importing <i><project_root>/platform/Neo4jPlatform
 * .xmi</i>, and provides the following actions:
 * <ul>
 * <li>{@link CreateNode}: creates a new node in the database</li>
 * <li>{@link AddProperty}: adds a property to a given node</li>
 * <li>{@link CreateRelationship}: creates a relationship between two
 * nodes</li>
 * </ul>
 */
public class Neo4jPlatform extends RuntimePlatform {

    /**
     * The {@link Configuration} key used to store the path of the Neo4j database to use with this platform.
     * <p>
     * The provided path can be relative or absolute.
     */
    public static final String NEO4J_DB_PATH_KEY = "xatkit.neo4j.db.path";

    /**
     * The {@link String} representing the path of the Neo4j database to use with this platform.
     */
    private String dbPath;

    /**
     * The raw {@link GraphDatabaseService} used to interact with the Neo4j database.
     * <p>
     * The {@link GraphDatabaseService} is initialized with the provided {@link #dbPath}.
     */
    private GraphDatabaseService dbService;

    /**
     * Constructs a new {@link Neo4jPlatform} with the provided {@link XatkitCore} and {@link Configuration}.
     * <p>
     * This constructor initializes the underlying connection to the Neo4j database using the database path provided
     * in the {@link Configuration}.
     * <p>
     * <b>Note:</b> {@link Neo4jPlatform} requires a valid database path to be initialized, and calling the default
     * constructor will throw an {@link IllegalArgumentException} when looking for the database path.
     *
     * @param xatkitCore    the {@link XatkitCore} instance associated to this runtime platform
     * @param configuration the {@link Configuration} used to retrieve the database path
     * @throws NullPointerException     if the provided {@code xatkitCore} or {@code configuration} is {@code null}
     * @throws IllegalArgumentException if the provided database path is {@code null} or empty
     */
    public Neo4jPlatform(XatkitCore xatkitCore, Configuration configuration) {
        super(xatkitCore, configuration);
        dbPath = configuration.getString(NEO4J_DB_PATH_KEY);
        checkArgument(nonNull(dbPath) && !dbPath.isEmpty(), "Cannot construct a Neo4j database with the provided " +
                "path: %s", dbPath);
        this.dbService = new GraphDatabaseFactory().newEmbeddedDatabase(new File(dbPath));
        registerShutdownHook(dbService);
    }

    /**
     * Returns the raw {@link GraphDatabaseService} used to interact with the Neo4j database.
     *
     * @return the raw {@link GraphDatabaseService} used to interact with the Neo4j database
     */
    public GraphDatabaseService getDbService() {
        return this.dbService;
    }

    /**
     * Registers a shutdown hook that properly closes the database when the JVM is stopped.
     *
     * @param dbService the {@link GraphDatabaseService} to close
     */
    private static void registerShutdownHook(final GraphDatabaseService dbService) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                dbService.shutdown();
            }
        });
    }

}
