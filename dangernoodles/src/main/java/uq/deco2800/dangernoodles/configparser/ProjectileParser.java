package uq.deco2800.dangernoodles.configparser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.dangernoodles.weapons.ProjectileDefinition;


/**
 * @author torusse
 * 
 */
public class ProjectileParser {
    private static final Logger logger = LoggerFactory.getLogger(
            ProjectileParser.class.getClass());
    
    private List<ProjectileDefinition> projectileList;

    /**
     * Constructor for the Projectile XML parser
     * 
     * @param fileName
     *            the file path of a valid Projectiles XML file
     */
    public ProjectileParser(String fileName) {
        try {
            File projectileFile = new File(fileName);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            
            // Process projectiles
            ProjectileHandler projectileHandler = new ProjectileHandler();
            parser.parse(projectileFile, projectileHandler);
            projectileList = projectileHandler.getProjectileList();
            
            // Process projectile clusters
            for (ProjectileDefinition pdef : projectileList) {
                if (pdef.makesClusters()) {
                    ProjectileDefinition cluster =
                            getClusterByID(pdef.getClusterID(), projectileList);
                    pdef.setClusterType(cluster);
                }
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    
    private ProjectileDefinition getClusterByID(int id, List<ProjectileDefinition> pdl) {
        for (ProjectileDefinition pd : pdl) {
            if (pd.getId() == id) {
                return pd;
            }
        }
        logger.warn("Projectile with ID: " + id + " was not found");
        return null;
    }

    /**
     * Getter method for the list of projectiles generated by the parser
     * 
     * @return a list of ProjectileDefinitions
     */
    public List<ProjectileDefinition> getProjectileList() {
        return new ArrayList<ProjectileDefinition>(projectileList);
    }

    @Override
    public String toString() {
        return projectileList.toString();
    }

}
