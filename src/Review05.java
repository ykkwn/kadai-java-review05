import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        //データベース接続と結果取得のための変数宣言
        final String URL = "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true";
        final String USER = "root";
        final String PASS = "****";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //①DBに接続
            con = DriverManager.getConnection(URL, USER, PASS);

            //②PreparedStatmentの作成
            String sql = "SELECT * FROM person WHERE id = ?";
            pstmt = con.prepareStatement(sql);

            //③SQLの実行と結果を格納/代入
            System.out.print("検索キーワードを入力してください > ");
            int input = keyIn();

            // PreparedStatementオブジェクトの?に値をセット
            pstmt.setInt(1, input);
            rs = pstmt.executeQuery();

            //④結果を表示
            while(rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.print(name + "\t" + age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }   finally {
                try {
                    if(rs != null) rs.close();
                    if(pstmt != null) pstmt.close();
                    if(con != null) con.close();
                }   catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    private static int keyIn() {
        int result = 0;
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                result = Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return result;
    }
}
