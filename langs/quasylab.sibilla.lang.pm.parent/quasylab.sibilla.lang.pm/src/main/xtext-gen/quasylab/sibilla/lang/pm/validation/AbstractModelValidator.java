/*
 * generated by Xtext 2.18.0.M3
 */
package quasylab.sibilla.lang.pm.validation;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;

public abstract class AbstractModelValidator extends AbstractDeclarativeValidator {
	
	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> result = new ArrayList<EPackage>();
		result.add(quasylab.sibilla.lang.pm.model.ModelPackage.eINSTANCE);
		return result;
	}
}