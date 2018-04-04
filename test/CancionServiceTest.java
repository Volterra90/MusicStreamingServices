import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.model.Cancion;
import com.musicstreaming.streaming.model.Contido;
import com.musicstreaming.streaming.service.CancionService;
import com.musicstreaming.streaming.service.impl.CancionServiceImpl;
import com.musicstreaming.streaming.util.ToStringUtil;

public class CancionServiceTest {

	private CancionService cancionService = null;
	private static Logger logger = LogManager.getLogger(CancionServiceTest.class.getName());

	public CancionServiceTest() {
		cancionService = new CancionServiceImpl();
	}

	
	protected void testFindByGrupo() {
		logger.info("Testing findbyGrupo ...");
		// Test data:		
		int pageSize = 2;
		Long id = 1L;

		try {

			List<Cancion> results = null;
			int startIndex = 1; 
			int total = 0;

			do {
				results = cancionService.findByGrupo(startIndex, pageSize, id);
				if (results.size()>0) {
					logger.info("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Contido c: results) {
						total++;
						logger.info("Result "+total+": "+ToStringUtil.toString(c));
					}
					startIndex = startIndex + pageSize;
				}

			} while (results.size()==pageSize);

			logger.info("Found "+total+" results.");

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);
		}
		logger.info("Test findByGrupo finished.\n");
	}

	public static void main(String args[]) {
		CancionServiceTest test = new CancionServiceTest();
		test.testFindByGrupo();
	}

}