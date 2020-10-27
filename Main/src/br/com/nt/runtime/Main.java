package br.com.nt.runtime;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.nt.beans.Aquisicao;
import weka.associations.Apriori;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class Main {

	public static void main(String[] args) {		
		String serverName="localhost";
		String mydatabase="weka";
		String url = "jdbc:mysql://" + serverName + ":3306/" + mydatabase + "?useTimezone=true&serverTimezone=UTC";
		String user = "";              
		String pwd = "";

		Connection connection = getConnection(url, user, pwd);		
		String query = new String("select * from associacao");

		ArrayList<Aquisicao> compras = queryCompra(connection, query);
		printList(compras);
		
		
		
		//WEKA!
		/*    */
		System.out.println("WEKA: ");
		try {
			InstanceQuery querie;
			querie = new InstanceQuery();
		
			querie.setCustomPropsFile(new File("resources/DatabaseUtils.props"));
			querie.setUsername("");
			querie.setPassword("");
			
			String sql = "select * from associacao";
			querie.setQuery(sql);	
			// if your data is sparse, then you can say so too
			// query.setSparseData(true);
			Instances data = querie.retrieveInstances();			
			System.out.println(data.toString());
			
			System.out.println("---");
			int[] indices = {0,3};
			Remove removeFilter = new Remove();
			removeFilter.setAttributeIndicesArray(indices);
			//removeFilter.setInvertSelection(true);
			removeFilter.setInputFormat(data);
			Instances filteredData = Filter.useFilter(data, removeFilter);	
			generateRule(filteredData, 10);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* */
	}

	public static ArrayList<Aquisicao> queryCompra(Connection con, String query){
		ArrayList<Aquisicao> compras = new ArrayList<Aquisicao>();
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int i=1;
				Aquisicao aquisicao = new Aquisicao();
				aquisicao.setId(rs.getInt(i++));
				aquisicao.setLeite(rs.getNString(i++));
				aquisicao.setCafe(rs.getNString(i++));
				aquisicao.setCerveja(rs.getNString(i++));
				aquisicao.setPao(rs.getNString(i++));
				aquisicao.setManteiga(rs.getNString(i++));
				aquisicao.setArroz(rs.getNString(i++));
				aquisicao.setFeijao(rs.getNString(i++));
				compras.add(aquisicao);
			}
			return compras;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static void printList(ArrayList<Aquisicao> compras) {
		int i=0;
		for (Aquisicao aquisicao: compras ) {
			System.out.printf("Posição %d- %s %s %s %s\n", i, aquisicao.getId(), aquisicao.getLeite(), aquisicao.getCafe(), aquisicao.getCerveja());
		    i++;
		}
	}
	
	public static void generateRule(Instances data, int numRules ){
		Apriori apriori = new Apriori();
		try {
			apriori.setUpperBoundMinSupport(12.5);
			apriori.setNumRules(numRules);
			apriori.buildAssociations(data);
			System.out.println(apriori);
			System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static Connection getConnection(String url, String user, String pwd) {
		try {
			return DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

