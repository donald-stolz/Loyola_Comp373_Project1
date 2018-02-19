package use;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import facility.FacilityDetail;
import use.UseDetail;
import use.Inspection;

public class Use extends FacilityDetail{

	private ArrayList<UseDetail> 	usageHistory;
	private ArrayList<Inspection>	inspections;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");


	public Use(String name, String info, int capacity) {
		super(name, info, capacity);

		usageHistory = new ArrayList<UseDetail>;
		inspections = new ArrayList<Inspection>;
	}

	public boolean objectIsInUseDuringInterval(String start, String end){
		Date checkStart = sdf.parse(start);
		Date checkEnd		= sdf.parse(end);
		for (int i = 0;i < usageHistory.getLength(); i++) {
			UseDetail reservation = usageHistory.get(i);

      Date reservationEnd = sdf.parse(reservation.getEnd());
      Date reservationStart = sdf.parse(reservation.getStart());

			if ((checkStart.compareTo(reservationStart) > 0 && checkStart.compareTo(reservationEnd) < 0) ||
						(checkEnd.compareTo(reservationStart) > 0 && checkEnd.compareTo(reservationEnd) < 0)	) {
					return true;
			}
		}
		return false;
	}

	public void assignFacilityToUse(String start, String end, String name, String info){
		if (objectIsInUseDuringInterval(start, end)){return;//TODO: Send a message to console}
		UseDetail reservation = new UseDetail(start, end, name, info);
		usageHistory.add(reservation);
	}

	public void vacateFacility(String start, String end){
		String name = "RESERVED";
		String info = "Facility has been asked to be vacated";
		if (objectIsInUseDuringInterval(start, end)) {

		}
		assignFacilityToUse(start, end, name, info);
	}

	public ArrayList<Inspection> listInspections(){
		return this.inspections;
	}

	public ArrayList<UseDetail> listActualUsage(){
		return this.usageHistory;
	}

	public double calcUsageRate(){
		// TODO calculate usage(hrs) per day
	}
}
