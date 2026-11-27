package _MedicalRecord;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import java.util.Objects;
 
@DataType()
public final class Patient {
 
	@Property()
	private final String id;
 
	@Property()
	private final String name;
 
	@Property()
	private final String diagnosis;
 
	@Property()
	private final String treatment;
 
	@Property()
	private final String dateofvisit;

	public String getId() {
		return id;
	}
 
	public String getName() {
		return name;
	}
	
	public String getDiagnosis() {
		return diagnosis;
	}
 
	public String getTreatment() {
		return treatment;
	}

	public String getDateofvisit() {
		return dateofvisit;
	}
 
	public Patient(@JsonProperty("id") final String id, @JsonProperty("name") final String name, @JsonProperty("diagnosis") final String diagnosis,
			@JsonProperty("treatment") final String treatment, @JsonProperty("dateofvisit") final String dateofvisit) {
		this.id = id;
		this.name = name;
		this.diagnosis = diagnosis;
		this.treatment = treatment;
		this.dateofvisit = dateofvisit;
	}
 
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
 
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
 
		Patient other = (Patient) obj;
 
		return Objects.deepEquals(new String[] { getId(), getName(), getDiagnosis(), getTreatment(), getDateofvisit() },
				new String[] { other.getId(), other.getName(), other.getDiagnosis(), other.getTreatment(), getDateofvisit() });
	}
 
	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getDiagnosis(), getTreatment(), getDateofvisit());
	}
 
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [id=" + id + ", name=" + name
				+ ", diagnosis=" + diagnosis + ", treatment=" + treatment + dateofvisit + ", dateofvisit=" + "]";
	}
 
}