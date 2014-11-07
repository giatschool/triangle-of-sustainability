package de.ifgi.data;

import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Literal;

public class MunicipalityDataItem implements Comparable {

	private String uri;
	private String mesoRegion;
	private String microRegion;

	private ArrayList<String> cellURI = new ArrayList<String>();
	private ArrayList<Double> defor2002 = new ArrayList<Double>();
	private ArrayList<Double> defor2003 = new ArrayList<Double>();
	private ArrayList<Double> defor2004 = new ArrayList<Double>();
	private ArrayList<Double> defor2005 = new ArrayList<Double>();
	private ArrayList<Double> defor2006 = new ArrayList<Double>();
	private ArrayList<Double> defor2007 = new ArrayList<Double>();
	private ArrayList<Double> defor2008 = new ArrayList<Double>();
	private ArrayList<Double> overlapPercentage = new ArrayList<Double>();
	private ArrayList<Double> overlapArea = new ArrayList<Double>();
	private ArrayList<Double> acum2002 = new ArrayList<Double>();
	private ArrayList<Double> acum2007 = new ArrayList<Double>();
	private ArrayList<Double> acum2008 = new ArrayList<Double>();
	private ArrayList<Double> past06 = new ArrayList<Double>();

	private ArrayList<Double> tempAgr06 = new ArrayList<Double>();
	private ArrayList<Double> permAgr06 = new ArrayList<Double>();

	private double totalDefor2002;
	private double totalDefor2003;
	private double totalDefor2004;
	private double totalDefor2005;
	private double totalDefor2006;
	private double totalDefor2007;
	private double totalDefor2008;

	private double soja04;
	private double soja05;
	private double soja06;
	private double soja07;
	private double soja08;

	private double cattle04;
	private double cattle05;
	private double cattle06;
	private double cattle07;
	private double cattle08;

	private double gdp04;
	private double gdp05;
	private double gdp06;
	private double gdp07;
	private double gdp08;

	private double pop2000;
	private double pop2001;
	private double pop2002;
	private double pop2003;
	private double pop2004;
	private double pop2005;
	private double pop2006;
	private double pop2007;
	private double pop2008;
	private double pop2009;

	private double area;

	private String name;
	private String centroid;
	private int numberOfCells;

	private String border;
	private String mesoBorder;
	private String microBorder;

	public MunicipalityDataItem(String uri, String cellURI, double defor2002,
			double defor2003, double defor2004, double defor2005,
			double defor2006, double defor2007, double defor2008,
			String border, double overlapArea, double overlapRatio,
			double pop2000, double pop2001, double pop2002, double pop2003,
			double pop2004, double pop2005, double pop2006, double pop2007,
			double pop2008, double pop2009, double acum2002, double acum2007,
			double acum2008, double past06, double soja04, double soja05,
			double soja06, double soja07, double soja08, double cattle04,
			double cattle05, double cattle06, double cattle07, double cattle08,
			double gdp04, double gdp05, double gdp06, double gdp07,
			double gdp08, double tempAgr06, double permAgr06,
			String mesoRegion, String microRegion, String mesoBorder,
			String microBorder, String name, String centroid) {
		super();
		this.uri = uri;
		this.cellURI.add(cellURI);
		this.defor2002.add(defor2002);
		this.defor2003.add(defor2003);
		this.defor2004.add(defor2004);
		this.defor2005.add(defor2005);
		this.defor2006.add(defor2006);
		this.defor2007.add(defor2007);
		this.defor2008.add(defor2008);
		this.acum2002.add(acum2002);
		this.acum2007.add(acum2007);
		this.acum2008.add(acum2008);
		this.past06.add(past06);
		this.soja04 = soja04;
		this.soja05 = soja05;
		this.soja06 = soja06;
		this.soja07 = soja07;
		this.soja08 = soja08;

		this.cattle04 = cattle04;
		this.cattle05 = cattle05;
		this.cattle06 = cattle06;
		this.cattle07 = cattle07;
		this.cattle08 = cattle08;

		this.gdp04 = gdp04;
		this.gdp05 = gdp05;
		this.gdp06 = gdp06;
		this.gdp07 = gdp07;
		this.gdp08 = gdp08;

		this.tempAgr06.add(tempAgr06);
		this.permAgr06.add(permAgr06);

		this.microRegion = microRegion;
		this.mesoRegion = mesoRegion;
		this.microBorder = microBorder;
		this.mesoBorder = mesoBorder;

		this.overlapArea.add(overlapArea);
		this.overlapPercentage.add(overlapRatio);
		this.border = border;
		this.pop2000 = pop2000;
		this.pop2001 = pop2001;
		this.pop2002 = pop2002;
		this.pop2003 = pop2003;
		this.pop2004 = pop2004;
		this.pop2005 = pop2005;
		this.pop2006 = pop2006;
		this.pop2007 = pop2007;
		this.pop2008 = pop2008;
		this.pop2009 = pop2009;
		this.name = name;
		this.centroid = centroid;
		numberOfCells = 1;
	}

	public void addEntry(String cellURI, double defor2002, double defor2003,
			double defor2004, double defor2005, double defor2006,
			double defor2007, double defor2008, double overlapArea,
			double overlapRatio, double acum2002, double acum2007,
			double acum2008, double past06, double soja04, double tempAgr06,
			double permAgr06) {

		this.cellURI.add(cellURI);
		this.defor2002.add(defor2002);
		this.defor2003.add(defor2003);
		this.defor2004.add(defor2004);
		this.defor2005.add(defor2005);
		this.defor2006.add(defor2006);
		this.defor2007.add(defor2007);
		this.defor2008.add(defor2008);
		this.acum2002.add(acum2002);
		this.acum2007.add(acum2007);
		this.acum2008.add(acum2008);
		this.past06.add(past06);
		this.tempAgr06.add(tempAgr06);
		this.permAgr06.add(permAgr06);

		this.overlapArea.add(overlapArea);
		this.overlapPercentage.add(overlapRatio);
		numberOfCells++;
	}

	@Override
	public int compareTo(Object obj) {
		if (this.uri.equals(((MunicipalityDataItem) obj).getUri())) {
			return 0;
		} else {
			return -1;
		}
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public ArrayList<String> getCellURI() {
		return cellURI;
	}

	public void setCellURI(ArrayList<String> cellURI) {
		this.cellURI = cellURI;
	}

	public ArrayList<Double> getDefor2002() {
		return defor2002;
	}

	public void setDefor2002(ArrayList<Double> defor2002) {
		this.defor2002 = defor2002;
	}

	public ArrayList<Double> getDefor2003() {
		return defor2003;
	}

	public void setDefor2003(ArrayList<Double> defor2003) {
		this.defor2003 = defor2003;
	}

	public ArrayList<Double> getDefor2004() {
		return defor2004;
	}

	public void setDefor2004(ArrayList<Double> defor2004) {
		this.defor2004 = defor2004;
	}

	public ArrayList<Double> getDefor2005() {
		return defor2005;
	}

	public void setDefor2005(ArrayList<Double> defor2005) {
		this.defor2005 = defor2005;
	}

	public ArrayList<Double> getDefor2006() {
		return defor2006;
	}

	public void setDefor2006(ArrayList<Double> defor2006) {
		this.defor2006 = defor2006;
	}

	public ArrayList<Double> getDefor2007() {
		return defor2007;
	}

	public void setDefor2007(ArrayList<Double> defor2007) {
		this.defor2007 = defor2007;
	}

	public ArrayList<Double> getDefor2008() {
		return defor2008;
	}

	public void setDefor2008(ArrayList<Double> defor2008) {
		this.defor2008 = defor2008;
	}

	public ArrayList<Double> getOverlapPercentage() {
		return overlapPercentage;
	}

	public void setOverlapPercentage(ArrayList<Double> overlapPercentage) {
		this.overlapPercentage = overlapPercentage;
	}

	public ArrayList<Double> getOverlapArea() {
		return overlapArea;
	}

	public void setOverlapArea(ArrayList<Double> overlapArea) {
		this.overlapArea = overlapArea;
	}

	public Double getTotalAcum2002() {

		double totalArea = 0;
		double acumTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			acumTimesArea += acum2002.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		double totalAcum2002 = acumTimesArea / totalArea;

		return totalAcum2002;
	}

	public Double getTotalAcum2003() {

		double totalAcum2002 = getTotalAcum2002();

		double rest = 100.0 - totalAcum2002;
		double totalAcum2003 = totalAcum2002 + rest / 100 * getTotalDefor2003();

		return totalAcum2003;
	}

	public Double getTotalAcum2004() {

		double totalAcum2003 = getTotalAcum2003();

		double rest = 100.0 - totalAcum2003;
		double totalAcum2004 = totalAcum2003 + rest / 100 * getTotalDefor2004();

		return totalAcum2004;
	}

	public Double getTotalAcum2005() {

		double totalAcum2004 = getTotalAcum2004();

		double rest = 100.0 - totalAcum2004;
		double totalAcum2005 = totalAcum2004 + rest / 100 * getTotalDefor2005();

		return totalAcum2005;
	}

	public Double getTotalAcum2006() {

		double totalAcum2005 = getTotalAcum2005();

		double rest = 100.0 - totalAcum2005;
		double totalAcum2006 = totalAcum2005 + rest / 100 * getTotalDefor2006();

		return totalAcum2006;
	}

	public Double getTotalAcum2007() {

		double totalArea = 0;
		double acumTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			acumTimesArea += acum2007.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		double totalAcum2007 = acumTimesArea / totalArea;

		return totalAcum2007;
	}

	public Double getTotalAcum2008() {

		double totalArea = 0;
		double acumTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			acumTimesArea += acum2008.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		double totalAcum2008 = acumTimesArea / totalArea;

		return totalAcum2008;
	}

	public Double getTotalPast06() {

		double totalArea = 0;
		double acumTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			acumTimesArea += past06.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		double past06 = acumTimesArea / totalArea;

		return past06;
	}

	public Double getTotalPermAgr06() {

		double totalArea = 0;
		double acumTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			acumTimesArea += permAgr06.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		double permAgr06 = acumTimesArea / totalArea;

		return permAgr06;
	}

	public Double getTotalTempAgr06() {

		double totalArea = 0;
		double acumTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			acumTimesArea += tempAgr06.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		double tempAgr06 = acumTimesArea / totalArea;

		return tempAgr06;
	}

	public Double getTotalDefor2002() {

		double totalArea = 0;
		double deforTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			deforTimesArea += defor2002.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		totalDefor2002 = deforTimesArea / totalArea;

		return totalDefor2002;
	}

	public void setTotalDefor2002(Double totalDefor2002) {
		this.totalDefor2002 = totalDefor2002;
	}

	public Double getTotalDefor2003() {
		double totalArea = 0;
		double deforTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			deforTimesArea += defor2003.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		totalDefor2003 = deforTimesArea / totalArea;

		return totalDefor2003;
	}

	public void setTotalDefor2003(Double totalDefor2003) {
		this.totalDefor2003 = totalDefor2003;
	}

	public Double getTotalDefor2004() {
		double totalArea = 0;
		double deforTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			deforTimesArea += defor2004.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		totalDefor2004 = deforTimesArea / totalArea;

		return totalDefor2004;
	}

	public void setTotalDefor2004(Double totalDefor2004) {
		this.totalDefor2004 = totalDefor2004;
	}

	public Double getTotalDefor2005() {
		double totalArea = 0;
		double deforTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			deforTimesArea += defor2005.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		totalDefor2005 = deforTimesArea / totalArea;

		return totalDefor2005;
	}

	public void setTotalDefor2005(Double totalDefor2005) {
		this.totalDefor2005 = totalDefor2005;
	}

	public Double getTotalDefor2006() {
		double totalArea = 0;
		double deforTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			deforTimesArea += defor2006.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		totalDefor2006 = deforTimesArea / totalArea;

		return totalDefor2006;
	}

	public void setTotalDefor2006(Double totalDefor2006) {
		this.totalDefor2006 = totalDefor2006;
	}

	public Double getTotalDefor2007() {
		double totalArea = 0;
		double deforTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			deforTimesArea += defor2007.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		totalDefor2007 = deforTimesArea / totalArea;

		return totalDefor2007;
	}

	public void setTotalDefor2007(Double totalDefor2007) {
		this.totalDefor2007 = totalDefor2007;
	}

	public Double getTotalDefor2008() {
		double totalArea = 0;
		double deforTimesArea = 0;

		for (int i = 0; i < cellURI.size(); i++) {
			deforTimesArea += defor2008.get(i) * overlapArea.get(i);
			totalArea += overlapArea.get(i);

		}
		totalDefor2008 = deforTimesArea / totalArea;

		return totalDefor2008;
	}

	public void setTotalDefor2008(Double totalDefor2008) {
		this.totalDefor2008 = totalDefor2008;
	}

	public int getNumberOfCells() {
		return numberOfCells;
	}

	public void setNumberOfCells(int numberOfCells) {
		this.numberOfCells = numberOfCells;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public double getPop2000() {
		return pop2000;
	}

	public void setPop2000(double pop2000) {
		this.pop2000 = pop2000;
	}

	public double getPop2001() {
		return pop2001;
	}

	public void setPop2001(double pop2001) {
		this.pop2001 = pop2001;
	}

	public double getPop2002() {
		return pop2002;
	}

	public void setPop2002(double pop2002) {
		this.pop2002 = pop2002;
	}

	public double getPop2003() {
		return pop2003;
	}

	public void setPop2003(double pop2003) {
		this.pop2003 = pop2003;
	}

	public double getPop2004() {
		return pop2004;
	}

	public void setPop2004(double pop2004) {
		this.pop2004 = pop2004;
	}

	public double getPop2005() {
		return pop2005;
	}

	public void setPop2005(double pop2005) {
		this.pop2005 = pop2005;
	}

	public double getPop2006() {
		return pop2006;
	}

	public void setPop2006(double pop2006) {
		this.pop2006 = pop2006;
	}

	public double getPop2007() {
		return pop2007;
	}

	public void setPop2007(double pop2007) {
		this.pop2007 = pop2007;
	}

	public double getPop2008() {
		return pop2008;
	}

	public void setPop2008(double pop2008) {
		this.pop2008 = pop2008;
	}

	public double getPop2009() {
		return pop2009;
	}

	public void setPop2009(double pop2009) {
		this.pop2009 = pop2009;
	}

	public void setTotalDefor2002(double totalDefor2002) {
		this.totalDefor2002 = totalDefor2002;
	}

	public void setTotalDefor2003(double totalDefor2003) {
		this.totalDefor2003 = totalDefor2003;
	}

	public void setTotalDefor2004(double totalDefor2004) {
		this.totalDefor2004 = totalDefor2004;
	}

	public void setTotalDefor2005(double totalDefor2005) {
		this.totalDefor2005 = totalDefor2005;
	}

	public void setTotalDefor2006(double totalDefor2006) {
		this.totalDefor2006 = totalDefor2006;
	}

	public void setTotalDefor2007(double totalDefor2007) {
		this.totalDefor2007 = totalDefor2007;
	}

	public void setTotalDefor2008(double totalDefor2008) {
		this.totalDefor2008 = totalDefor2008;
	}

	public ArrayList<Double> getAcum2002() {
		return acum2002;
	}

	public void setAcum2002(ArrayList<Double> acum2002) {
		this.acum2002 = acum2002;
	}

	public ArrayList<Double> getAcum2007() {
		return acum2007;
	}

	public void setAcum2007(ArrayList<Double> acum2007) {
		this.acum2007 = acum2007;
	}

	public ArrayList<Double> getAcum2008() {
		return acum2008;
	}

	public void setAcum2008(ArrayList<Double> acum2008) {
		this.acum2008 = acum2008;
	}

	public ArrayList<Double> getPast06() {
		return past06;
	}

	public void setPast06(ArrayList<Double> past06) {
		this.past06 = past06;
	}

	public ArrayList<Double> getTempAgr06() {
		return tempAgr06;
	}

	public void setTempAgr06(ArrayList<Double> tempAgr06) {
		this.tempAgr06 = tempAgr06;
	}

	public ArrayList<Double> getPermAgr06() {
		return permAgr06;
	}

	public void setPermAgr06(ArrayList<Double> permAgr06) {
		this.permAgr06 = permAgr06;
	}

	public double getSoja04() {
		return soja04;
	}

	public void setSoja04(double soja04) {
		this.soja04 = soja04;
	}

	public double getSoja05() {
		return soja05;
	}

	public void setSoja05(double soja05) {
		this.soja05 = soja05;
	}

	public double getSoja06() {
		return soja06;
	}

	public void setSoja06(double soja06) {
		this.soja06 = soja06;
	}

	public double getSoja07() {
		return soja07;
	}

	public void setSoja07(double soja07) {
		this.soja07 = soja07;
	}

	public double getSoja08() {
		return soja08;
	}

	public void setSoja08(double soja08) {
		this.soja08 = soja08;
	}

	public double getCattle04() {
		return cattle04;
	}

	public void setCattle04(double cattle04) {
		this.cattle04 = cattle04;
	}

	public double getCattle05() {
		return cattle05;
	}

	public void setCattle05(double cattle05) {
		this.cattle05 = cattle05;
	}

	public double getCattle06() {
		return cattle06;
	}

	public void setCattle06(double cattle06) {
		this.cattle06 = cattle06;
	}

	public double getCattle07() {
		return cattle07;
	}

	public void setCattle07(double cattle07) {
		this.cattle07 = cattle07;
	}

	public double getCattle08() {
		return cattle08;
	}

	public void setCattle08(double cattle08) {
		this.cattle08 = cattle08;
	}

	public String getMesoRegion() {
		return mesoRegion;
	}

	public void setMesoRegion(String mesoRegion) {
		this.mesoRegion = mesoRegion;
	}

	public String getMicroRegion() {
		return microRegion;
	}

	public void setMicroRegion(String microRegion) {
		this.microRegion = microRegion;
	}

	public String getMesoBorder() {
		return mesoBorder;
	}

	public void setMesoBorder(String mesoBorder) {
		this.mesoBorder = mesoBorder;
	}

	public String getMicroBorder() {
		return microBorder;
	}

	public void setMicroBorder(String microBorder) {
		this.microBorder = microBorder;
	}

	public double getArea() {
		double tempArea = 0;
		for (double area : overlapArea)
			tempArea += area;
		this.area = tempArea;
		return tempArea;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getGdp04() {
		return gdp04;
	}

	public void setGdp04(double gdp04) {
		this.gdp04 = gdp04;
	}

	public double getGdp05() {
		return gdp05;
	}

	public void setGdp05(double gdp05) {
		this.gdp05 = gdp05;
	}

	public double getGdp06() {
		return gdp06;
	}

	public void setGdp06(double gdp06) {
		this.gdp06 = gdp06;
	}

	public double getGdp07() {
		return gdp07;
	}

	public void setGdp07(double gdp07) {
		this.gdp07 = gdp07;
	}

	public double getGdp08() {
		return gdp08;
	}

	public void setGdp08(double gdp08) {
		this.gdp08 = gdp08;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCentroid() {
		return centroid;
	}

	public void setCentroid(String centroid) {
		this.centroid = centroid;
	}

}
