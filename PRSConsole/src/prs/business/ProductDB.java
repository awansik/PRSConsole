package prs.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import prs.util.DBUtil;

public class ProductDB {

	public List<Product> getAll() {
        String sql = "SELECT * FROM Product";
        List<Product> products = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product p = getProductFromResultSet(rs);
            	products.add(p);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return products;
    }
	
	public Product getProduct(int pid) {
	        String sql = "SELECT * FROM Product WHERE ID = ?";
	        Product product = null;
	        try (Connection connection = DBUtil.getConnection();
	             PreparedStatement ps = connection.prepareStatement(sql);
	             ) {
	        	ps.setInt(1, pid);
	        	ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                product = getProductFromResultSet(rs);
	            }
	        } catch (SQLException e) {
	            System.err.println(e);
	            return null;
	        }
	        return product;
	    }
	
	public boolean add(Product p) {
	        String sql = "INSERT INTO product (VendorID,PartNumber,Name,Price) " + 
	        		" VALUES (?, ?, ?, ?)";
	        try (Connection connection = DBUtil.getConnection();
	             PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setInt(1, p.getVendorID());
	            ps.setString(2, p.getPartNumber());
	            ps.setString(3, p.getName());
	            ps.setDouble(4, p.getPrice());
	            ps.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            System.err.println(e);
	            return false;
	    }
	}

	public boolean update(Product p) {
        String sql = "UPDATE product SET Name = ?" + 
        		" WHERE id = ?;";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(2, p.getId());
            ps.setString(1, p.getName());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
	}

	public boolean delete(Product p) {
        String sql = "DELETE from product" + 
        		" WHERE id = ?;";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, p.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
	}
	
	private Product getProductFromResultSet(ResultSet rs) throws SQLException {
		int id = rs.getInt(1);
		int vid = rs.getInt(2);
		String partNum = rs.getString(3);
		String name = rs.getString(4);
		double price = rs.getDouble(5);
		String  unit = rs.getString(6);
		String photoPath = rs.getString(7);
		Product p = new Product(id, vid, partNum, 
				name, price, unit, 
				photoPath);
		return p;
	}
}
