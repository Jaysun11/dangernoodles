package uq.deco2800.dangernoodles.configparser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.dangernoodles.weapons.ProjectileDefinitionList;
import uq.deco2800.dangernoodles.weapons.WeaponDefinition;

/**
 * @author torusse
 *
 */
public class WeaponParser {
    
    private static final Logger logger = LoggerFactory.getLogger(WeaponParser.class.getClass());
    
    private List<WeaponDefinition> weaponList;

    /**
     * Constructor for the Weapon XML parser
     * 
     * @param fileName
     *            the file path of a valid Weapons XML file
     */
    public WeaponParser(String fileName, ProjectileDefinitionList projectiles) {
        try {
            File weaponFile = new File(fileName);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            WeaponHandler weaponHandler = new WeaponHandler(projectiles);
            parser.parse(weaponFile, weaponHandler);

            weaponList = weaponHandler.getWeaponList();

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("Weapon failed to parse: {}", e.getMessage());
        }
    }

    /**
     * Getter method for the list of weapons generated by the parser
     * 
     * @return a list of WeaponDefinitions
     */
    public List<WeaponDefinition> getWeaponList() {
        List<WeaponDefinition> list = new ArrayList<WeaponDefinition>();
        list.addAll(weaponList);
        return list;
    }

    @Override
    public String toString() {
        return weaponList.toString();
    }

}
