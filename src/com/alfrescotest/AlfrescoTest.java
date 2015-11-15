package com.alfrescotest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Property;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.Properties;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

public class AlfrescoTest {

	private static String USER = "admin";
	private static String PASSWORD = "admin";
	private static String URL = "http://127.0.0.1:8080/alfresco/api/-default-/public/cmis/versions/1.1/browser/";
	
	private static String ID_FOLDER_PRUEBA = "e1a85d7c-6193-49a7-aa90-8e1a3d2e6b0f";
	
	public static void main (String args[]) {
		
		
		// default factory implementation
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<String, String>();

		// user credentials
		parameter.put(SessionParameter.USER, USER);
		parameter.put(SessionParameter.PASSWORD, PASSWORD);

		// connection settings
		parameter.put(SessionParameter.BROWSER_URL, URL);
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER.value());
		
		List<Repository> repositories = factory.getRepositories(parameter);
		Session session = repositories.get(0).createSession();
		
		Folder root = session.getRootFolder();
		System.out.println("Root: " + root.getName());

		ItemIterable<CmisObject> children = root.getChildren();

		/*System.out.println("Folders hijos de root: " + root.getName());
		for (CmisObject o : children) {
		  System.out.println(o.getName());
		  System.out.println(o.getId());
		}*/
		
		//System.out.println("Listando Folder Prueba");
		CmisObject object = session.getObject(session.createObjectId(ID_FOLDER_PRUEBA));
		Folder folder = (Folder) object;
		System.out.println("Folder: " + folder.getName());
		ItemIterable<CmisObject> childrenPrueba = folder.getChildren();
		
		/*for (CmisObject o : childrenPrueba) {
		  System.out.println(o.getName());
		  System.out.println(o.getId());
		  System.out.println(o.getProperties());
		}*/
		
		CmisObject o = childrenPrueba.iterator().next();
		Document d = (Document) o;
		System.out.println("Doc: " + d.getName());
		
		Map<String, Property> properties = new HashMap<String, Property>();
		List<Property<?>> propertiesList = d.getProperties();
		for (Property p: propertiesList) {
			System.out.println(p.getId());
			System.out.println(p.getValue());
			properties.put(p.getId(), p);
		}
	}
	
}
