package _MedicalRecord;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import com.owlike.genson.Genson;
 
 
@Contract(
        name = "PatientRecord",
        info = @Info(
                title = "PatientRecord contract",
                description = "Record the Patient Details in Hyperledger Fabric",
                version = "0.0.1-SNAPSHOT"))
 
 
@Default
public final class PatientRecordUpdate implements ContractInterface {
 
	private final Genson genson = new Genson();
	private enum PatientRecordErrors {
	        PATIENT_RECORD_NOT_FOUND,
	        PATIENT_RECORD_ALREADY_EXISTS
	    }
	
	
	/**
     * Add some initial properties to the ledger
     *
     * @param ctx the transaction context
     */
    @Transaction()
    public void initLedger(final Context ctx) {
    	
        ChaincodeStub stub= ctx.getStub();
        
        Patient Patient = new Patient("1", "Alice", "Diabetes", "Lowering sugar level", "18-Apr-2026");
        
        String PatientState = genson.serialize(Patient);
        
        stub.putStringState("1", PatientState);
    }
    
    
    /**
     * Add new Patient on the ledger.
     *
     * @param ctx the transaction context
     * @param id the key for the new Patient
     * @param name the name of the new Patient
     * @param diagnosis the diagnosis of the new Patient
     * @param treatment the treatment of the new Patient
     * @param dateofvisit the dateofvisit of the new Patient
     * @return the created Patient
     */
	
    @Transaction()
    public Patient addNewPatient(final Context ctx, final String id, final String name,
            final String diagnosis, final String treatment, final String dateofvisit) {
        
    	ChaincodeStub stub = ctx.getStub();
 
        String PatientState = stub.getStringState(id);
        
        if (!PatientState.isEmpty()) {
            String errorMessage = String.format("Patient Id %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, PatientRecordErrors.PATIENT_RECORD_ALREADY_EXISTS.toString());
        }
        
        Patient Patient = new Patient(id, name, diagnosis, treatment, dateofvisit);
        
        PatientState = genson.serialize(Patient);
        
        stub.putStringState(id, PatientState); 
        
        return Patient;
    }
 
 
    	/**
	     * Retrieves a Patient based upon Patient Id from the ledger.
	     *
	     * @param ctx the transaction context
	     * @param id the key
	     * @return the Patient found on the ledger if there was one
	     */
    	@Transaction()
	    public Patient queryPatientById(final Context ctx, final String id) {
	        ChaincodeStub stub = ctx.getStub();
	        String PatientState = stub.getStringState(id);
 
	        if (PatientState.isEmpty()) {
	            String errorMessage = String.format("Patient Id: %s does not exist", id);
	            System.out.println(errorMessage);
	            throw new ChaincodeException(errorMessage, PatientRecordErrors.PATIENT_RECORD_NOT_FOUND.toString());
	        }
	        
	        Patient Patient = genson.deserialize(PatientState, Patient.class);
	        return Patient;
	    }
    	
        /**
   	     * Changes the treatment of a patient on the ledger.
   	     *
   	     * @param ctx the transaction context
   	     * @param id the key
   	     * @param newtreatment the new treatment
   	     * @return the updated Patient
   	     */
   	    @Transaction()
   	    public Patient changeTreatment(final Context ctx, final String id, final String newTreatment) {
   	        ChaincodeStub stub = ctx.getStub();
 
   	        String PatientState = stub.getStringState(id);
 
   	        if (PatientState.isEmpty()) {
   	            String errorMessage = String.format("Patient Id: %s does not exist", id);
   	            System.out.println(errorMessage);
   	            throw new ChaincodeException(errorMessage, PatientRecordErrors.PATIENT_RECORD_NOT_FOUND.toString());
   	        }
 
   	        Patient Patient = genson.deserialize(PatientState, Patient.class);
 
   	        Patient newPatient = new Patient(Patient.getId(), Patient.getName(), Patient.getDiagnosis(), newTreatment, Patient.getDateofvisit());
   	        
   	        String newPatientState = genson.serialize(newPatient);
   	        
   	        stub.putStringState(id, newPatientState);
 
   	        return newPatient;
   	    } 
}
