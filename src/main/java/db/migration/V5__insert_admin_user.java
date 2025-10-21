package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import java.sql.PreparedStatement;

public class V5__insert_admin_user extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        try (PreparedStatement stmt = context.getConnection().prepareStatement(
                "INSERT INTO login (usuario, senha, tipo_usuario) VALUES (?, ?, ?) ON CONFLICT (usuario) DO NOTHING"
        )) {
            stmt.setString(1, "admin_java");
            stmt.setString(2, "$2a$10$...hashedpassword..."); // senha criptografada
            stmt.setString(3, "ADMINISTRADOR");
            stmt.execute();
        }
    }
}
