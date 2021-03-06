package facility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import maintenance.Issue;
import maintenance.Maintenance;
import maintenance.Record;
import maintenance.Request;
import use.Inspection;
import use.Use;
import use.UseDetail;

public class FacilityImpl implements Facility{
	private FacilityDetail 	details;
	private List<Facility> 	subfacilities;
	private Maintenance 		maintenance;
	private Use							use;

	public FacilityImpl(){ }

	public void setFacilityDetails(FacilityDetail details){this.details = details;}

	public FacilityDetail getFacilityDetails(){return this.details;}

	public String toString(){return ("Facility :[ Name : " + details.getName() + ", info : " + details.getInfo() + ", capacity :" + details.getCapacity()+" ]");}

	public void setFacility(List<Facility> subfacilities) {this.subfacilities = subfacilities;}

	public List<Facility> getFacility(){return this.subfacilities;}

	public void setMaintenance(Maintenance maintenance){this.maintenance = maintenance;}

	public Maintenance getMaintenance(){return this.maintenance;}

	public void setUse(Use use){this.use = use;}

	public Use getUse(){return this.use;}

//Details
	@Override
	public void setName(String name) {details.setName(name);}

	@Override
	public String getName() {return details.getName();}

	@Override
	public void setInfo(String info) {details.setInfo(info);}
	@Override
	public String getInfo() {return details.getInfo();}

	@Override
	public void setCapacity(int capacity) {details.setCapacity(capacity);}

	@Override
	public int getCapacity() {return details.getCapacity();}

//Facility
	public int numFacilities() {
		int total = 1;
		for (Facility subFacility : this.getFacility()) {
			total += subFacility.numFacilities();
		}
		return total;
	}

	public void addNewFacility(Facility subFacility){subfacilities.add(subFacility);}

	public void removeFacility(Facility subFacility){subfacilities.remove(subFacility);}

	public void vacateFacility(UseDetail vacate) throws ParseException{
		removeObjectsDuringInterval(vacate.getStart(), vacate.getEnd());
		for (Facility subfacility : subfacilities) {
			subfacility.vacateFacility(vacate);
		}
		vacate.setInfo("Facility has been asked to be vacated");
		vacate.setName("RESERVED");

		assignFacilityToUse(vacate);
	}

	public long totalMaintenanceCost(){
		long subCosts = 0;
		for (Facility subFacility : subfacilities) {
			subCosts += subFacility.totalMaintenanceCost();
		}
		long costs = this.calcMaintenanceCost() + subCosts;
		return costs;
	}

	public double avgTotalProblemRate(){
		long sumRate = 0;
		for (Facility subFacility : subfacilities) {
			//double uses   = this.getUseDetails().size();
			sumRate += subFacility.calcProblemRate();
		}
		double avg = sumRate / this.numFacilities();
		return avg;
	}


//Use
	public void setSimpleDateFormat(SimpleDateFormat sdf){use.setSimpleDateFormat(sdf);}
	public SimpleDateFormat getSimpleDateFormat(){return use.getSimpleDateFormat();}
	public void setUseDetails(List<UseDetail> useDetails){use.setUseDetails(useDetails);}
	public List<UseDetail> getUseDetails(){return use.getUseDetails();}
	public void setInspections(List<Inspection> inspections){use.setInspections(inspections);}
	public List<Inspection> getInspections(){return use.getInspections();}
	public void assignFacilityToUse(UseDetail reservation) throws ParseException{use.assignFacilityToUse(reservation);}
	public boolean objectIsInUseDuringInterval(String start, String end) throws ParseException{return use.objectIsInUseDuringInterval(start, end);}
	public void removeObjectsDuringInterval(String start, String end) throws ParseException{use.removeObjectsDuringInterval(start,end);}
	public void performInspection(Inspection inspect){use.performInspection(inspect);}
	public double calcUsageRate() throws ParseException{return use.calcUsageRate();}

//Maintenance
	public void addIssue(Issue issue){maintenance.addIssue(issue);}
	public void removeIssue(Issue fixed){maintenance.removeIssue(fixed);}
	public void removeIssue(String id){maintenance.removeIssue(id);}
	public List<Issue> getIssues(){return maintenance.getIssues();}
	public void setIssues(List<Issue> issues){maintenance.setIssues(issues);}
	public void makeFacilityMaintRequest(Request request){maintenance.makeFacilityMaintRequest(request);}
	//public void makeFacilityMaintRequest(Issue issue){maintenance.makeFacilityMaintRequest(issue);}
	public List<Request> getRequests(){return maintenance.getRequests();}
	public void setRequests(List<Request> requests){maintenance.setRequests(requests);}
	public void addMaintRecord(Request request,  Record record, String dateCompleted, String workerName){
		maintenance.addMaintRecord(request, record, dateCompleted, workerName);}
	public List<Record> getRecords(){return maintenance.getRecords();}
	public void setRecords(List<Record> records){maintenance.setRecords(records);}
	public long calcMaintenanceCost(){return maintenance.calcMaintenanceCost();}
	public long calcDowntime(){return maintenance.calcDowntime();}
	public double calcProblemRate(){
		double uses = this.getUseDetails().size();
		return maintenance.calcProblemRate(uses);
	}
	public double calcProblemRate(double uses) {return maintenance.calcProblemRate(uses);}
}
