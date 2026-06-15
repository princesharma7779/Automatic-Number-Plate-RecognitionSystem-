package NoPlate.DetectionSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
public class SavePlate {

    public static void save(String plate) throws Exception {

        Connection con = DBConnection.getConnection();

        String sql =
                "INSERT INTO vehicle_records(plate_number) VALUES(?)";

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setString(1, plate);

        ps.executeUpdate();

        System.out.println("Data Saved Successfully");

        con.close();
    }
}