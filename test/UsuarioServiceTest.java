import com.musicstreaming.streaming.model.Direccion;
import com.musicstreaming.streaming.model.Usuario;
import com.musicstreaming.streaming.util.PasswordEncryptionUtil;
import com.sacra.ecommerce.model.Transportista;
import com.sacra.ecommerce.model.User;
import com.sacra.ecommerce.service.UserService;
import com.sacra.ecommerce.service.UserServiceTest;
import com.sacra.ecommerce.service.impl.MockUserServiceImpl;
import com.sacra.ecommerce.util.ToStringUtil;

public class UsuarioServiceTest {

	private UsuarioService usuarioService = null;

	public UserServiceTest() {
		usuarioService = new UsuarioServiceImpl();
	}

	protected void testFindById() {

		logger.info("Testing findById ...");
		// Test data
		Long id = 1L;

		try {

			Usuario u = usuarioService.findById(id);
		} catch (Throwable t) {
			logger.error("id = " + id, t);
		}
		logger.info("Test testFindById finished.\n");

	}

	protected void testCreate() {
		logger.info("Testing create ...");

		// Test data:
		Usuario u = new Usuario();
		u.setApelidos("Taboada Varela");
		String plainPassword = "1234.,";
		u.setContrasinal(PasswordEncryptionUtil.encryptPassword(plainPassword));
		u.setEmail("topomusero@gmail.com");
		u.setFechaNacemento("1990/12/02");
		u.setFechaSubscricion("2018/03/15");
		u.setNick("Volterra_90");
		u.setNome("Alberto");
		u.setXenero('H');
		
		
		u.setDireccion(d);
		try {

			u = usuarioService.create(u);

			logger.info("Created: " + ToStringUtil.toString(u));

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
		logger.info("Test created finished.\n");
	}

	public static void main(String args[]) {
		UserServiceTest test = new UserServiceTest();
		test.testUserNotFound();
		test.testUserNotFound();
	}

}
