package lk.sliit.pafassignment;

import java.sql.*;


public class Customer {

    private static final String DATABASE_CONNECTION_ERROR_MESSAGE = "Error while connecting to the database for inserting.";

    private Connection connect() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electro_grid", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }


    public String insert(String firstName, String lastName, String nic, String mobileNo, String email, String address) {
        String output = "";
        try {
            Connection con = connect();
            if (con == null) {
                return DATABASE_CONNECTION_ERROR_MESSAGE;
            }

            String query = "INSERT INTO customer (first_name, last_name, nic, mobile_no, email, address) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, nic);
            preparedStatement.setString(4, mobileNo);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, address);

            preparedStatement.execute();
            con.close();

            String newCustomer = readCustomers();
            output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";

        } catch (Exception e) {
            output = "{\"status\":\"error\", \"data\":\"Error while inserting the customer.\"}";
            e.printStackTrace();
        }
        return output;

    }

    public String update(Long customerId, String firstName, String lastName, String nic, String mobileNo, String email, String address) {

        String output = "";
        try {
            Connection con = connect();
            if (con == null) {
                return DATABASE_CONNECTION_ERROR_MESSAGE;
            }

            String query = "UPDATE customer SET first_name=?, last_name=?, nic=?, mobile_no=?, email=?, address=? WHERE id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, nic);
            preparedStatement.setString(4, mobileNo);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, address);
            preparedStatement.setLong(7, customerId);


            preparedStatement.execute();
            con.close();

            String newCustomer = readCustomers();
            output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";
        } catch (Exception e) {
            output = "{\"status\":\"error\", \"data\":\"Error while updating the customer.\"}";
            e.printStackTrace();
        }
        return output;

    }

    public String delete(Long customerId) {
        String output = "";
        try {
            Connection con = connect();
            if (con == null) {
                return DATABASE_CONNECTION_ERROR_MESSAGE;
            }

            String query = "delete from customer where id=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setLong(1, customerId);

            preparedStmt.execute();
            con.close();

            String newCustomer = readCustomers();
            output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";

        } catch (Exception e) {
            output = "{\"status\":\"error\", \"data\":\"Error while deleting the customer.\"}";
            e.printStackTrace();
        }
        return output;
    }

    public String readCustomers() {
        String output = "";
        try {
            Connection con = connect();
            if (con == null) {
                return DATABASE_CONNECTION_ERROR_MESSAGE;
            }


            output = "<table class='table'>" +
                    "<thead><tr><th>First Name</th><th>Last Name</th>" +
                    "<th >NIC</th><th>Mobile No</th><th>Email</th>" +
                    "<th>Address</th><th>Update</th><th>Delete</th></tr>" +
                    "</thead><tbody>";

            String query = "select * from customer";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String nic = resultSet.getString("nic");
                String mobileNo = resultSet.getString("mobile_no");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");

                output += "<tr>" +
                        "<td>" + firstName + "</td>" +
                        "<td>" + lastName + "</td>" +
                        "<td>" + nic + "</td>" +
                        "<td>" + mobileNo + "</td>" +
                        "<td>" + email + "</td>" +
                        "<td>" + address + "</td>" +
                        "<td><button type='button' class='btn btn-outline-warning update-btn ' data-customerId='" + id + "'>Update</button></td>" +
                        "<td><button type='button' class='btn btn-outline-danger remove-btn ' data-customerId='" + id + "'>Remove</button></td>" +
                        "</tr>";


            }
            con.close();

            output += "</tbody></table>";
        } catch (Exception e) {
            output = "Error while reading the items.";
            e.printStackTrace();
        }
        return output;
    }


}
