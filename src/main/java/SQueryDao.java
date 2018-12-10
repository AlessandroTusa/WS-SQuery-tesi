import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;


public class SQueryDao {




    public static Query correggi (String query){
        final String sql="select distinct queryEseguita,testoMessaggio,SUGGERIMENTO,stato from dati where queryEseguita = ?";
        Query t =null;

        try {
            Connection conn = DBconnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1,query);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                t = new Query(rs.getString("queryEseguita"), rs.getString("testoMessaggio"),
                        rs.getString("SUGGERIMENTO"), rs.getInt("stato"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }
}