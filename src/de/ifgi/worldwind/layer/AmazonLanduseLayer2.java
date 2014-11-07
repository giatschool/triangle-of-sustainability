package de.ifgi.worldwind.layer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.reasoner.rulesys.builtins.GreaterThan;

import de.ifgi.data.MesoRegion;
import de.ifgi.data.MicroRegion;
import de.ifgi.data.MunicipalityDataItem;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.ExtrudedPolygon;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Polyline;

import gov.nasa.worldwind.render.ScreenAnnotation;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.airspaces.AirspaceAttributes;
import gov.nasa.worldwind.render.airspaces.Curtain;
import gov.nasa.worldwind.render.airspaces.Polygon;
import gov.nasa.worldwind.render.airspaces.Route;
import gov.nasa.worldwindx.examples.util.PowerOfTwoPaddedImage;

public class AmazonLanduseLayer2 extends RenderableLayer {

	// static ArrayList<Literal> border;
	// static ArrayList<Literal> label;
	// static ArrayList<Literal> pop0;
	// static ArrayList<Literal> pop1;
	// static ArrayList<Literal> pop2;
	// static ArrayList<Literal> pop3;
	// static ArrayList<Literal> pop4;
	// static ArrayList<Literal> pop5;
	// static ArrayList<Literal> pop6;
	// static ArrayList<Literal> pop7;
	// static ArrayList<Literal> pop8;
	// static ArrayList<Literal> pop9;

	ShapeAttributes sideAttributesPlain;
	ShapeAttributes sideHighlightAttributesPlain;
	ShapeAttributes capAttributesPlain;

	ShapeAttributes sideAttributes;
	ShapeAttributes sideHighlightAttributes;
	ShapeAttributes capAttributes;

	ShapeAttributes sideAttributes2;
	ShapeAttributes sideHighlightAttributes2;
	ShapeAttributes capAttributes2;

	ShapeAttributes sideAttributes3;
	ShapeAttributes sideHighlightAttributes3;
	ShapeAttributes capAttributes3;

	ShapeAttributes sideAttributes4;
	ShapeAttributes sideHighlightAttributes4;
	ShapeAttributes capAttributes4;

	ShapeAttributes normalAttributes;

	ArrayList<MunicipalityDataItem> muniData;
	ArrayList<MesoRegion> mesoRegions;

	String name;
	String nameMin;
	String nameMax;
	LatLon pos;
	LatLon posMin;
	LatLon posMax;
	String year = "";

	DecimalFormat f = new DecimalFormat(",##0.00");
	DecimalFormat f2 = new DecimalFormat(",##0");

	AnnotationLayer annotationLayer = new AnnotationLayer();
	AnnotationLayer generalAnnotationLayer = new AnnotationLayer();
	String amazonRegion = "-45.3021476801,-1.42261406018;-45.3531185735,-1.73701446065;-45.1015508796,-1.36079972099;-45.079027782,-1.51917969123;-44.8162665717,-1.41872861919;-44.8982981162,-1.61413480287;-44.6430544844,-1.6246604203;-44.8463813436,-1.82910413191;-44.593924687,-1.74452766863;-44.4130981563,-2.4134617802;-44.0003495509,-2.4661982097;-44.0003516566,-6.75854771829;-44.6882870732,-7.39496275691;-45.4563112759,-7.67109510383;-45.9610830649,-8.819313121;-45.7841421121,-9.48054315858;-45.9556120402,-10.2188811388;-45.697845915,-10.2640041526;-46.617419071,-11.2898915038;-46.0869951119,-11.6225787368;-46.3152805806,-11.632694005;-46.3749057849,-11.8690420296;-46.1715870227,-11.9014299316;-46.3981876607,-12.0407842952;-46.1542937169,-12.483669162;-46.3051088764,-12.9503195756;-46.1146375956,-12.9184137053;-46.3641968185,-12.9916641854;-46.4179404133,-12.823918849;-47.4273055782,-13.2898839338;-47.6351111036,-13.1049408198;-47.679315453,-13.4681583234;-48.1657367334,-13.3062333759;-48.1469863656,-13.1524552525;-48.4420484414,-13.2928124189;-48.5092020949,-13.1292946252;-48.5867458672,-13.3180573061;-48.8578392476,-12.8058612284;-49.2377541839,-12.8844094915;-49.3698346117,-13.275040411;-50.2929920082,-12.8403562156;-50.1430199287,-12.3963691007;-50.3125341009,-12.4982157883;-50.8721389227,-13.7335313672;-51.0844318091,-14.9168480088;-51.652448159,-15.1803184574;-51.8799602581,-15.8258378273;-52.5258891802,-16.141000154;-52.6361136897,-16.5520778072;-53.0399823166,-16.9124016787;-53.2515721373,-17.6195234488;-53.0723500973,-18.0399166362;-53.9492813346,-17.9238698608;-53.706520834,-17.6628386101;-53.7090188381,-17.2286117569;-54.0850588857,-17.6194781535;-54.5820327937,-17.4684501131;-55.1276277636,-17.6531639631;-56.1134909587,-17.1676359383;-56.7342305817,-17.310300461;-57.0452932577,-17.7307603725;-57.4528493004,-17.9030818971;-57.7284377948,-17.5301858048;-58.3959128539,-17.1848934266;-58.3214638173,-16.2651852218;-60.1721468843,-16.2660372571;-60.2389929239,-15.4742811597;-60.5654061707,-15.1091120933;-60.2447051437,-15.0972371978;-60.4676735436,-13.7961583612;-61.0153204984,-13.4879625116;-61.8414998513,-13.5493386746;-62.1708944994,-13.1143464558;-62.7795199822,-13.0099941166;-63.1555618701,-12.6168629772;-64.2913574336,-12.5018197083;-65.0325260538,-11.9954877589;-65.3618461063,-11.2517599904;-65.395443891,-9.68617760199;-65.5608737534,-9.84506377006;-66.6205113862,-9.89443691824;-68.544026182,-11.11075613;-69.4172778708,-10.9278629035;-70.6231089286,-11.0004365849;-70.494761621,-9.42686853649;-71.2136171486,-9.96736448427;-72.2017761541,-10.0006265688;-72.2860407373,-9.54036572821;-73.2158486935,-9.41170073162;-72.9450028509,-8.98733046964;-73.9949173962,-7.53665090582;-73.9678681277,-7.34795764044;-73.7039833694,-7.30465887009;-73.7102578918,-6.83967107176;-73.141001005,-6.49808655334;-73.2400863408,-6.03231111378;-72.8753821087,-5.15626421986;-71.8846433156,-4.51645507742;-70.9444208846,-4.38618187546;-70.6547573511,-4.1275299335;-69.964873755,-4.30099631335;-69.3966171594,-1.1329907557;-69.6120517933,-0.513845353618;-70.0584842175,-0.187170353632;-70.044613692,0.558426416664;-69.1160101781,0.649441790603;-69.2662372282,1.06395811956;-69.8453796261,1.08497209949;-69.8435522488,1.72036316067;-68.1575191034,1.73123312018;-68.1836282504,1.97839397051;-67.9419758705,1.83033102092;-67.3898790395,2.24361268327;-67.0979718293,1.73220337777;-67.0887486944,1.16650132819;-66.8575033511,1.2298116062;-66.318980801,0.754645942371;-65.58604406,1.00853848143;-65.541014837,0.648396125024;-65.1035502092,1.15626062231;-64.3979064951,1.52644421253;-64.3384592114,1.36312957258;-63.9959529697,1.97950415344;-63.3727284775,2.21134781602;-63.4072596973,2.43554874916;-64.0565108869,2.49729178182;-64.1862358643,3.55923122929;-64.7973717083,4.28534598683;-64.1648505811,4.12589767326;-63.9645661065,3.86756765227;-63.2053815161,3.95089844447;-62.9606493164,3.60729253533;-62.4631430088,4.17756888389;-61.9311871701,4.10284284983;-61.3235972539,4.5345077534;-60.9967297855,4.51701534795;-60.5922787058,4.92646360181;-60.6975579113,5.22840601098;-60.1364388404,5.24868402873;-59.990043982,4.97007773147;-60.1644954948,4.50840531487;-59.6728261392,4.37284843882;-59.5167056687,3.94368789188;-59.8722731321,3.56283133551;-59.9711606969,2.59313313848;-59.7228229406,2.2764602225;-59.6905936587,1.75590877107;-58.8547591195,1.18311324388;-58.4966934272,1.26762369773;-58.3228356448,1.59668180992;-58.0046910922,1.50269593104;-57.9905555534,1.65811167504;-57.5380533182,1.70017400232;-57.30757027,1.99633854152;-55.9568104143,1.84478908905;-55.9035351737,2.04077512494;-56.1393099494,2.26544673099;-55.9709506631,2.52901967581;-55.3857535952,2.41806676682;-55.0041591626,2.59064131395;-54.4369310534,2.20949943067;-53.7675669885,2.37853703191;-52.9063377302,2.185134706;-52.5551873841,2.51458767864;-51.5143778303,4.43636939061;-51.0759495447,3.89023681067;-50.7028672631,2.13782101528;-50.446472785,2.19885493415;-50.2384729906,1.80279366582;-49.9153865636,1.69513109842;-50.0615842641,0.338036647172;-49.6779650015,0.365579321437;-48.9310370682,-0.226320114334;-48.4125844483,-0.257634310031;-48.4729248301,-0.49946420274;-47.9973014495,-0.705167450781;-47.704242146,-0.535651473402;-47.46949223,-0.766107854507;-47.3001876651,-0.599239380157;-46.9357659175,-0.876981075065;-46.6384270715,-0.788667572926;-46.6756563109,-0.976628676614;-46.4273137406,-0.858309004102;-46.4833701191,-1.03811057127;-46.3058720004,-1.08474989147;-46.2049783519,-0.886755698807;-46.2731428203,-1.17238590936;-46.171902149,-0.993238348974;-45.9487917804,-1.23902784433;-45.8417595625,-1.0450112044;-45.8014868537,-1.34483501793;-45.6799915574,-1.13868179795;-45.7260313942,-1.41776196157;-45.5804804357,-1.25765802432;-45.5192300755,-1.41249596103;-45.402468808,-1.30810234778;-45.4814078461,-1.53736722794;-45.3021476801,-1.42261406018";

	int minPos = 0;
	int maxPos = 0;
	boolean shortu = true;
	double minpop = 1000000000;
	double maxpop = 0;

	private final static PowerOfTwoPaddedImage redPic = PowerOfTwoPaddedImage
			.fromPath("images/red.png");
	private final static PowerOfTwoPaddedImage greenPic = PowerOfTwoPaddedImage
			.fromPath("images/green.png");

	private final static PowerOfTwoPaddedImage brownPic = PowerOfTwoPaddedImage
			.fromPath("images/brown.jpg");
	private final static PowerOfTwoPaddedImage yellowPic = PowerOfTwoPaddedImage
			.fromPath("images/yellow2.jpg");

	private final static PowerOfTwoPaddedImage ifgiPic = PowerOfTwoPaddedImage
			.fromPath("images/logo-rgb-ifgi-text-de.jpg");
	private final static PowerOfTwoPaddedImage columnPic = PowerOfTwoPaddedImage
			.fromPath("images/columnLegend.jpg");
	private final static PowerOfTwoPaddedImage arcPic = PowerOfTwoPaddedImage
			.fromPath("images/exportLegend.jpg");

	private final static PowerOfTwoPaddedImage trees = PowerOfTwoPaddedImage
			.fromPath("images/bäume3.jpg");
	private final static PowerOfTwoPaddedImage badPic = PowerOfTwoPaddedImage
			.fromPath("images/luftbild.jpg");

	public AmazonLanduseLayer2(ArrayList<MunicipalityDataItem> muniData,
			ArrayList<MesoRegion> mesoRegions, String year) {
		super();
		this.year = year;
		this.muniData = muniData;
		this.mesoRegions = mesoRegions;
		// this.border = border;
		// this.label = label;
		// this.pop0 = pop0;
		// this.pop1 = pop1;
		// this.pop2 = pop2;
		// this.pop3 = pop3;
		// this.pop4 = pop4;
		// this.pop5 = pop5;
		// this.pop6 = pop6;
		// this.pop7 = pop7;
		// this.pop8 = pop8;
		// this.pop9 = pop9;
		setAttributes();
		drawPolygons();

	}

	public void setAttributes() {

		sideAttributesPlain = new BasicShapeAttributes();
		sideAttributesPlain.setInteriorMaterial(Material.WHITE);
		sideAttributesPlain.setOutlineOpacity(0.5);
		sideAttributesPlain.setInteriorOpacity(0.5);
		sideAttributesPlain.setOutlineMaterial(Material.WHITE);
		sideAttributesPlain.setOutlineWidth(1);
		sideAttributesPlain.setDrawOutline(true);
		sideAttributesPlain.setDrawInterior(true);
		sideAttributesPlain.setEnableLighting(true);

		sideHighlightAttributesPlain = new BasicShapeAttributes(
				sideAttributesPlain);
		sideHighlightAttributesPlain.setOutlineMaterial(Material.WHITE);
		sideHighlightAttributesPlain.setOutlineOpacity(0.05);

		capAttributesPlain = new BasicShapeAttributes(sideAttributesPlain);
		capAttributesPlain.setInteriorMaterial(Material.WHITE);
		capAttributesPlain.setInteriorOpacity(0.5);
		capAttributesPlain.setDrawInterior(true);
		capAttributesPlain.setEnableLighting(true);

		sideAttributes = new BasicShapeAttributes();
		sideAttributes.setInteriorMaterial(Material.GREEN);
		sideAttributes.setOutlineOpacity(0.5);
		sideAttributes.setInteriorOpacity(0.5);
		sideAttributes.setOutlineMaterial(Material.GREEN);
		sideAttributes.setOutlineWidth(2);
		sideAttributes.setDrawOutline(true);
		sideAttributes.setDrawInterior(true);
		sideAttributes.setEnableLighting(true);

		sideHighlightAttributes = new BasicShapeAttributes(sideAttributes);
		sideHighlightAttributes.setOutlineMaterial(Material.WHITE);
		sideHighlightAttributes.setOutlineOpacity(1);

		capAttributes = new BasicShapeAttributes(sideAttributes);
		capAttributes.setInteriorMaterial(Material.GREEN);
		capAttributes.setInteriorOpacity(0.8);
		capAttributes.setDrawInterior(true);
		capAttributes.setEnableLighting(true);

		sideAttributes2 = new BasicShapeAttributes();
		sideAttributes2.setInteriorMaterial(Material.RED);
		sideAttributes2.setOutlineOpacity(0.5);
		sideAttributes2.setInteriorOpacity(0.5);
		sideAttributes2.setOutlineMaterial(Material.RED);
		sideAttributes2.setOutlineWidth(2);
		sideAttributes2.setDrawOutline(true);
		sideAttributes2.setDrawInterior(true);
		sideAttributes2.setEnableLighting(true);

		sideHighlightAttributes2 = new BasicShapeAttributes(sideAttributes2);
		sideHighlightAttributes2.setOutlineMaterial(Material.WHITE);
		sideHighlightAttributes2.setOutlineOpacity(1);

		capAttributes2 = new BasicShapeAttributes(sideAttributes2);
		capAttributes2.setInteriorMaterial(Material.RED);
		capAttributes2.setInteriorOpacity(0.8);
		capAttributes2.setDrawInterior(true);
		capAttributes2.setEnableLighting(true);

		Material brown = new Material(new Color(153, 102, 51));
		sideAttributes3 = new BasicShapeAttributes();
		sideAttributes3.setInteriorMaterial(brown);
		sideAttributes3.setOutlineOpacity(0.5);
		sideAttributes3.setInteriorOpacity(0.5);
		sideAttributes3.setOutlineMaterial(brown);
		sideAttributes3.setOutlineWidth(2);
		sideAttributes3.setDrawOutline(true);
		sideAttributes3.setDrawInterior(true);
		sideAttributes3.setEnableLighting(true);

		sideHighlightAttributes3 = new BasicShapeAttributes(sideAttributes3);
		sideHighlightAttributes3.setOutlineMaterial(Material.WHITE);
		sideHighlightAttributes3.setOutlineOpacity(1);

		capAttributes3 = new BasicShapeAttributes(sideAttributes3);
		capAttributes3.setInteriorMaterial(brown);
		capAttributes3.setOutlineMaterial(Material.YELLOW);

		capAttributes3.setInteriorOpacity(0.8);
		capAttributes3.setDrawInterior(true);
		capAttributes3.setEnableLighting(true);

		sideAttributes4 = new BasicShapeAttributes();
		sideAttributes4.setInteriorMaterial(Material.ORANGE);
		sideAttributes4.setOutlineOpacity(1);
		sideAttributes4.setInteriorOpacity(1);
		sideAttributes4.setOutlineMaterial(Material.ORANGE);
		sideAttributes4.setOutlineWidth(2);
		sideAttributes4.setDrawOutline(true);
		sideAttributes4.setDrawInterior(true);
		sideAttributes4.setEnableLighting(true);

		sideHighlightAttributes4 = new BasicShapeAttributes(sideAttributes4);
		sideHighlightAttributes4.setOutlineMaterial(Material.WHITE);
		sideHighlightAttributes4.setOutlineOpacity(1);

		capAttributes4 = new BasicShapeAttributes(sideAttributes4);
		capAttributes4.setInteriorMaterial(Material.ORANGE);
		capAttributes4.setInteriorOpacity(1);
		capAttributes4.setDrawInterior(true);
		capAttributes4.setEnableLighting(true);

		normalAttributes = new BasicShapeAttributes();
		normalAttributes.setInteriorMaterial(Material.YELLOW);
		normalAttributes.setOutlineOpacity(0.5);
		normalAttributes.setInteriorOpacity(0.8);
		normalAttributes.setOutlineMaterial(Material.GREEN);
		normalAttributes.setOutlineWidth(2);
		normalAttributes.setDrawOutline(true);
		normalAttributes.setDrawInterior(true);
		normalAttributes.setEnableLighting(true);
	}

	public void drawPolygons() {
		ExtrudedPolygon pgon;
		Polygon pgon2;
		Polygon pgon3;

		double pop = 0;

		// double maxDefor=0;
		// double minDefor=2000000;
		for (int j = 0; j < muniData.size(); j++) {

			pop = muniData.get(j).getTotalAcum2002();
			// System.out.println(muniData.get(j).getTotalPast06());
			if (pop > maxpop) {
				maxpop = pop;
				maxPos = j;
			}
			if (pop < minpop) {
				minpop = pop;
				minPos = j;
			}

		}

		String[] amazonStringArray = amazonRegion.split(";");
		ArrayList<Position> amazonPositions = new ArrayList<Position>();
		for (String str : amazonStringArray) {
			String[] splitStr = str.split(",");
			amazonPositions.add(Position.fromDegrees(
					Double.parseDouble(splitStr[1]),
					Double.parseDouble(splitStr[0])));
		}
		Polyline polyLine = new Polyline(amazonPositions);
		polyLine.setColor(Color.GREEN);
		polyLine.setFollowTerrain(true);
		polyLine.setLineWidth(4);

		addRenderable(polyLine);

		GlobeAnnotation ga = new GlobeAnnotation("Amazonas Region",
				polyLine.getReferencePosition(), Font.decode("Arial-BOLD-12"));
		// ga.getAttributes().setDefaults(defaultAttributes);
		ga.getAttributes().setBackgroundColor(new Color(0.2f, 0.2f, 0.2f, .5f));
		ga.getAttributes().setTextColor(Color.WHITE);
		ga.getAttributes().setBorderColor(Color.BLACK);

		// annotationLayer.addAnnotation(ga);

		for (int i = 0; i < muniData.size(); i++) {
			String[] borderStringArray = muniData.get(i).getBorder().split(";");

			double totalPasturePercentage = 0.0;
			double totalAgriculturePercentage = 0.0;
			double totalArea = 0.0;
			double totalAcumDeforestation = 0.0;

			if (year == "2003") {
				totalAcumDeforestation = muniData.get(i).getTotalAcum2003()
						* muniData.get(i).getArea();
				totalArea = muniData.get(i).getArea();

			}
			if (year == "2004") {
				totalAcumDeforestation = muniData.get(i).getTotalAcum2004()
						* muniData.get(i).getArea();
				totalArea = muniData.get(i).getArea();
			}
			if (year == "2005") {
				totalAcumDeforestation = muniData.get(i).getTotalAcum2005()
						* muniData.get(i).getArea();
				totalArea = muniData.get(i).getArea();
			}
			if (year == "2006") {
				totalAcumDeforestation = muniData.get(i).getTotalAcum2006()
						* muniData.get(i).getArea();
				totalArea = muniData.get(i).getArea();
			}
			if (year == "2007") {
				// totalAcumDeforestation = muniData.get(i).getTotalAcum2006();

			}
			if (year == "2008") {
				// totalAcumDeforestation = muniData.get(i).getTotalAcum2006();

			}

			for (int k = 0; k < muniData.get(i).getOverlapArea().size(); k++) {
				if (year == "2002") {
					totalPasturePercentage += muniData.get(i).getPast06()
							.get(k)
							* muniData.get(i).getOverlapArea().get(k);
					totalAgriculturePercentage += (muniData.get(i)
							.getTempAgr06().get(k) + muniData.get(i)
							.getPermAgr06().get(k))
							* muniData.get(i).getOverlapArea().get(k);
					totalAcumDeforestation += muniData.get(i).getAcum2002()
							.get(k)
							* muniData.get(i).getOverlapArea().get(k);
					totalArea += muniData.get(i).getOverlapArea().get(k);
				}
				if (year == "2003") {
					totalPasturePercentage += muniData.get(i).getPast06()
							.get(k)
							* muniData.get(i).getOverlapArea().get(k);
					totalAgriculturePercentage += (muniData.get(i)
							.getTempAgr06().get(k) + muniData.get(i)
							.getPermAgr06().get(k))
							* muniData.get(i).getOverlapArea().get(k);
				}

				if (year == "2004") {
					totalPasturePercentage += muniData.get(i).getPast06()
							.get(k)
							* muniData.get(i).getOverlapArea().get(k);
					totalAgriculturePercentage += (muniData.get(i)
							.getTempAgr06().get(k) + muniData.get(i)
							.getPermAgr06().get(k))
							* muniData.get(i).getOverlapArea().get(k);

				}
				if (year == "2005") {
					totalPasturePercentage += muniData.get(i).getPast06()
							.get(k)
							* muniData.get(i).getOverlapArea().get(k);
					totalAgriculturePercentage += (muniData.get(i)
							.getTempAgr06().get(k) + muniData.get(i)
							.getPermAgr06().get(k))
							* muniData.get(i).getOverlapArea().get(k);

				}
				if (year == "2006") {
					totalPasturePercentage += muniData.get(i).getPast06()
							.get(k)
							* muniData.get(i).getOverlapArea().get(k);
					totalAgriculturePercentage += (muniData.get(i)
							.getTempAgr06().get(k) + muniData.get(i)
							.getPermAgr06().get(k))
							* muniData.get(i).getOverlapArea().get(k);

				}
				if (year == "2007") {
					totalPasturePercentage += muniData.get(i).getPast06()
							.get(k)
							* muniData.get(i).getOverlapArea().get(k);
					totalAgriculturePercentage += (muniData.get(i)
							.getTempAgr06().get(k) + muniData.get(i)
							.getPermAgr06().get(k))
							* muniData.get(i).getOverlapArea().get(k);
					totalAcumDeforestation += muniData.get(i).getAcum2007()
							.get(k)
							* muniData.get(i).getOverlapArea().get(k);
					totalArea += muniData.get(i).getOverlapArea().get(k);
				}
				if (year == "2008") {
					totalPasturePercentage += muniData.get(i).getPast06()
							.get(k)
							* muniData.get(i).getOverlapArea().get(k);
					totalAgriculturePercentage += (muniData.get(i)
							.getTempAgr06().get(k) + muniData.get(i)
							.getPermAgr06().get(k))
							* muniData.get(i).getOverlapArea().get(k);
					totalAcumDeforestation += muniData.get(i).getAcum2008()
							.get(k)
							* muniData.get(i).getOverlapArea().get(k);
					totalArea += muniData.get(i).getOverlapArea().get(k);
				}
			}
			// System.out.println(totalArea);

			totalAcumDeforestation = totalAcumDeforestation / totalArea;
			totalAgriculturePercentage = totalAgriculturePercentage / totalArea;
			totalPasturePercentage = totalPasturePercentage / totalArea;

			ArrayList<Position> borderPositions = new ArrayList<Position>();
			for (String str : borderStringArray) {
				String[] splitStr = str.split(",");
				borderPositions.add(Position.fromDegrees(
						Double.parseDouble(splitStr[1]),
						Double.parseDouble(splitStr[0]),
						(totalPasturePercentage + totalAgriculturePercentage)
								* 1000000 * totalAcumDeforestation / 2));
			}

			pgon = new ExtrudedPolygon(borderPositions);

			ArrayList<Position> borderPositions2 = new ArrayList<Position>();
			for (String str : borderStringArray) {
				String[] splitStr = str.split(",");
				// borderPositions2.add(Position.fromDegrees(Double.parseDouble(splitStr[1]),
				// Double.parseDouble(splitStr[0]), new Double("1000000")));
				borderPositions2.add(Position.fromDegrees(
						Double.parseDouble(splitStr[1]),
						Double.parseDouble(splitStr[0])));

			}
			// ArrayList<Position> tempBorders = new ArrayList<Position>();
			// for(int k = borderPositions2.size()-1;k >=0;k--){
			// tempBorders.add(new Position(borderPositions2.get(k).latitude,
			// borderPositions2.get(k).longitude, pop *1000000 +1000000));
			// }
			// // borderPositions2.addAll(tempBorders);

			// ArrayList<String> textures = new ArrayList<String>();
			// textures.add("images/cow.png");
			//
			pgon2 = new Polygon(borderPositions2);
			//
			pgon2.setAltitudes(
					(totalPasturePercentage + totalAgriculturePercentage)
							* 1000000 / 2 * totalAcumDeforestation + 1,
					1000000 / 2 * totalAcumDeforestation);
			Material yellow = new Material(new Color(255, 204, 51));
			pgon2.getAttributes().setMaterial(yellow);
			pgon2.getAttributes().setOutlineMaterial(yellow);
			pgon2.getAttributes().setOpacity(0.8);
			pgon2.getAttributes().setDrawOutline(true);
			pgon2.getAttributes().setDrawInterior(true);
			pgon2.getAttributes().setOutlineMaterial(Material.BLACK);
			// pgon2.getAttributes().setOutlineMaterial(Material.YELLOW);

			addRenderable(pgon2);

			// pgon3 = new Polygon(borderPositions2);
			// //
			// pgon3.setAltitudes(totalAgriculturePercentage/totalArea *1000000
			// *totalAcumDeforestation + totalPasturePercentage/totalArea
			// *1000000 *totalAcumDeforestation+1, 1000000
			// *totalAcumDeforestation);
			// pgon3.getAttributes().setMaterial(Material.GRAY);
			// addRenderable(pgon3);
			// pgon2.getAttributes()

			name = muniData.get(i).getUri();
			pgon.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
			// pgon2.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
			// SETTING COLOR ACCORDING TO DEFORESTATION
			// if(defor < 0.01){
			// pgon.setSideAttributes(sideAttributes);
			// pgon.setSideHighlightAttributes(sideHighlightAttributes);
			// pgon.setCapAttributes(capAttributes);
			// }
			// if(defor< 0.02 && defor > 0.01){
			// pgon.setSideAttributes(sideAttributes3);
			// pgon.setSideHighlightAttributes(sideHighlightAttributes3);
			// pgon.setCapAttributes(capAttributes3);
			//
			// }
			// if(defor >0.02 && defor <0.04){
			// pgon.setSideAttributes(sideAttributes4);
			// pgon.setSideHighlightAttributes(sideHighlightAttributes4);
			// pgon.setCapAttributes(capAttributes4);
			// }
			// if(defor >0.04){
			// pgon.setSideAttributes(sideAttributes2);
			// pgon.setSideHighlightAttributes(sideHighlightAttributes2);
			// pgon.setCapAttributes(capAttributes2);
			// }

			// pgon2.setAttributes(normalAttributes);
			// pgon2.setSideHighlightAttributes(sideHighlightAttributesPlain);
			// pgon2.setCapAttributes(capAttributesPlain);

			pgon.setSideAttributes(sideAttributes3);
			pgon.setSideHighlightAttributes(sideHighlightAttributes3);
			pgon.setCapAttributes(capAttributes3);

			addRenderable(pgon);

			String[] splitStr = muniData.get(i).getCentroid().split(",");

			if (muniData.get(i).getName().equals("Santarém")
					|| muniData.get(i).getName().contains("Belém")
					|| muniData.get(i).getName().contains("Altamira")
					|| i == maxPos) {
				GlobeAnnotation ga2 = new GlobeAnnotation(muniData.get(i)
						.getName()
						+ "\nAbholzung: "
						+ f2.format(totalAcumDeforestation * 100) + "%",
						Position.fromDegrees(Double.parseDouble(splitStr[1]),
								Double.parseDouble(splitStr[0]),
								1000000 / 2 * totalAcumDeforestation),
						Font.decode("Arial-BOLD-12"));
				ga2.getAttributes().setBorderColor(Color.BLACK);
				ga2.getAttributes().setBackgroundColor(
						new Color(0.2f, 0.2f, 0.2f, .5f));
				ga2.getAttributes().setTextColor(Color.WHITE);
				ga2.getAttributes().setBorderWidth(1);
				ga2.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIT_TEXT);
				ga2.setAlwaysOnTop(true);
				ga2.setMaxActiveAltitude(5000000.0);
				annotationLayer.addAnnotation(ga2);
			}

			// if(i ==maxPos){
			// addPictureLayer(Position.fromDegrees(Double.parseDouble(splitStr[1]),
			// Double.parseDouble(splitStr[0]), pop), badPic, 0.5);
			// }
			// else if (i == minPos){
			// addPictureLayer(Position.fromDegrees(Double.parseDouble(splitStr[1]),
			// Double.parseDouble(splitStr[0]), pop), trees,0.2);
			//
			// }

		}
		drawArc();

		//
		// }
		// System.out.println("min: "+minDefor +" max: "+maxDefor);
	}

	public void drawArc() {

		double unitedKingdomExport04 = 99527;
		double russiaExport04 = 160623;
		double chileExport04 = 105163;
		double egyptExport04 = 122665;
		double exportGermany04 = 28233;
		double exportUSA04 = 56114;

		double unitedKingdomExport05 = 118597;
		double russiaExport05 = 306163;
		double chileExport05 = 67126;
		double egyptExport05 = 152539;
		double exportGermany05 = 29190;
		double exportUSA05 = 52544;

		double unitedKingdomExport06 = 111929;
		double russiaExport06 = 371733;
		double chileExport06 = 4700;
		double egyptExport06 = 220800;
		double exportGermany06 = 23114;
		double exportUSA06 = 60232;

		double unitedKingdomExport07 = 66998;
		double russiaExport07 = 461676;
		double chileExport07 = 3000;
		double egyptExport07 = 183927;
		double exportGermany07 = 24471;
		double exportUSA07 = 86484;

		double unitedKingdomExport08 = 53718;
		double russiaExport08 = 390481;
		double chileExport08 = 4011;
		double egyptExport08 = 77793;
		double exportGermany08 = 9263;
		double exportUSA08 = 56057;

		double unitedKingdomExport = 0;
		double russiaExport = 0;
		double chileExport = 0;
		double egyptExport = 0;
		double exportGermany = 0;
		double exportUSA = 0;

		if (year == "2004") {
			unitedKingdomExport = unitedKingdomExport04;
			russiaExport = russiaExport04;
			chileExport = chileExport04;
			egyptExport = egyptExport04;
			exportGermany = exportGermany04;
			exportUSA = exportUSA04;
		}
		if (year == "2005") {
			unitedKingdomExport = unitedKingdomExport05;
			russiaExport = russiaExport05;
			chileExport = chileExport05;
			egyptExport = egyptExport05;
			exportGermany = exportGermany05;
			exportUSA = exportUSA05;
		}
		if (year == "2006") {
			unitedKingdomExport = unitedKingdomExport06;
			russiaExport = russiaExport06;
			chileExport = chileExport06;
			egyptExport = egyptExport06;
			exportGermany = exportGermany06;
			exportUSA = exportUSA06;
		}
		if (year == "2007") {
			unitedKingdomExport = unitedKingdomExport07;
			russiaExport = russiaExport07;
			chileExport = chileExport07;
			egyptExport = egyptExport07;
			exportGermany = exportGermany07;
			exportUSA = exportUSA07;
		}
		if (year == "2008") {
			unitedKingdomExport = unitedKingdomExport08;
			russiaExport = russiaExport08;
			chileExport = chileExport08;
			egyptExport = egyptExport08;
			exportGermany = exportGermany08;
			exportUSA = exportUSA08;
		}

		Route ukRoute = new Route();
		ukRoute.setAltitudes(40000.0, 42000.0);
		ukRoute.setWidth(unitedKingdomExport);
		ukRoute.setLocations(Arrays.asList(
				LatLon.fromDegrees(-1.845384, -43.952638),
				LatLon.fromDegrees(51.508742, -2.512208)
		// LatLon.fromDegrees(44.0, -120.0),
		// LatLon.fromDegrees(43.0, -120.0)
				));
		// route.setTerrainConforming(false, false);
		ukRoute.getAttributes().setMaterial(Material.YELLOW);
		ukRoute.getAttributes().setOutlineOpacity(1);
		// curtain.getAttributes().setOutlineWidth(10);
		// curtain.setPathType(AVKey.RHUMB_LINE);
		ukRoute.setTerrainConforming(false, false);
		ukRoute.getAttributes().setOpacity(0.8);
		ukRoute.getAttributes().setOutlineMaterial(Material.BLACK);
		ukRoute.getAttributes().setDrawOutline(true);
		ukRoute.getAttributes().setDrawInterior(true);
		Iterator<? extends LatLon> posis = ukRoute.getLocations().iterator();
		posis.next();
		addRenderable(exportAnnotation(new Position(posis.next(), 42000.0),
				"Großbritannien: " + f2.format(unitedKingdomExport)));
		addRenderable(ukRoute);

		Route russiaRoute = new Route();
		russiaRoute.setAltitudes(60000.0, 62000.0);
		russiaRoute.setWidth(russiaExport);

		russiaRoute.setLocations(Arrays.asList(
				LatLon.fromDegrees(-0.703107, -68.408207),
				LatLon.fromDegrees(32.990236, -156.826175),
				LatLon.fromDegrees(49.724479, 137.958981)

		// LatLon.fromDegrees(44.0, -120.0),
		// LatLon.fromDegrees(43.0, -120.0)
				));
		// route2.setTerrainConforming(false, false);
		russiaRoute.getAttributes().setMaterial(Material.YELLOW);
		russiaRoute.getAttributes().setOutlineOpacity(1);
		// curtain.getAttributes().setOutlineWidth(10);
		// curtain.setPathType(AVKey.RHUMB_LINE);
		russiaRoute.setTerrainConforming(false, false);
		russiaRoute.getAttributes().setOpacity(0.8);
		Iterator<? extends LatLon> posis2 = russiaRoute.getLocations()
				.iterator();
		posis2.next();
		addRenderable(exportAnnotation(new Position(posis2.next(), 42000.0),
				"Russland: " + f2.format(russiaExport)));
		// attributesPlain.setInteriorOpacity(1);
		russiaRoute.getAttributes().setOutlineMaterial(Material.BLACK);
		russiaRoute.getAttributes().setDrawOutline(true);
		russiaRoute.getAttributes().setDrawInterior(true);

		addRenderable(russiaRoute);

		Route chileRoute = new Route();
		chileRoute.setAltitudes(40000.0, 42000.0);
		chileRoute.setWidth(chileExport);

		chileRoute.setLocations(Arrays.asList(
				LatLon.fromDegrees(-12.554564, -60.043947),
				// LatLon.fromDegrees(21.371244,-156.196291),
				LatLon.fromDegrees(-23.241346, -69.536135)

		// LatLon.fromDegrees(44.0, -120.0),
		// LatLon.fromDegrees(43.0, -120.0)
				));
		// route2.setTerrainConforming(false, false);
		chileRoute.getAttributes().setMaterial(Material.YELLOW);
		chileRoute.getAttributes().setOutlineOpacity(1);
		// curtain.getAttributes().setOutlineWidth(10);
		// curtain.setPathType(AVKey.RHUMB_LINE);
		chileRoute.setTerrainConforming(false, false);
		chileRoute.getAttributes().setOpacity(0.8);
		Iterator<? extends LatLon> posis3 = chileRoute.getLocations()
				.iterator();
		posis3.next();
		addRenderable(exportAnnotation(new Position(posis3.next(), 42000.0),
				"Chile: " + f2.format(chileExport)));
		// attributesPlain.setInteriorOpacity(1);
		chileRoute.getAttributes().setOutlineMaterial(Material.BLACK);
		chileRoute.getAttributes().setDrawOutline(true);
		chileRoute.getAttributes().setDrawInterior(true);

		addRenderable(chileRoute);

		Route egyptRoute = new Route();
		egyptRoute.setAltitudes(40000.0, 42000.0);
		egyptRoute.setWidth(egyptExport);

		egyptRoute.setLocations(Arrays.asList(
				LatLon.fromDegrees(-5.615986, -35.698244),
				// LatLon.fromDegrees(21.371244,-156.196291),
				LatLon.fromDegrees(29.382175, 29.428709)

		// LatLon.fromDegrees(44.0, -120.0),
		// LatLon.fromDegrees(43.0, -120.0)
				));
		// route2.setTerrainConforming(false, false);
		egyptRoute.getAttributes().setMaterial(Material.YELLOW);
		egyptRoute.getAttributes().setOutlineOpacity(1);
		// curtain.getAttributes().setOutlineWidth(10);
		// curtain.setPathType(AVKey.RHUMB_LINE);
		egyptRoute.setTerrainConforming(false, false);
		egyptRoute.getAttributes().setOpacity(0.8);
		Iterator<? extends LatLon> posis4 = egyptRoute.getLocations()
				.iterator();
		posis4.next();
		addRenderable(exportAnnotation(new Position(posis4.next(), 42000.0),
				"Ägypten: " + f2.format(egyptExport)));
		// attributesPlain.setInteriorOpacity(1);
		egyptRoute.getAttributes().setOutlineMaterial(Material.BLACK);
		egyptRoute.getAttributes().setDrawOutline(true);
		egyptRoute.getAttributes().setDrawInterior(true);

		addRenderable(egyptRoute);

		Route germanyRoute = new Route();
		germanyRoute.setAltitudes(40000.0, 42000.0);
		germanyRoute.setWidth(exportGermany);

		germanyRoute.setLocations(Arrays.asList(
				LatLon.fromDegrees(-2.635789, -39.4043),
				// LatLon.fromDegrees(21.371244,-156.196291),
				LatLon.fromDegrees(49.724479, 9.462887)

		// LatLon.fromDegrees(44.0, -120.0),
		// LatLon.fromDegrees(43.0, -120.0)
				));
		// route2.setTerrainConforming(false, false);
		germanyRoute.getAttributes().setMaterial(Material.YELLOW);
		germanyRoute.getAttributes().setOutlineOpacity(1);
		// curtain.getAttributes().setOutlineWidth(10);
		// curtain.setPathType(AVKey.RHUMB_LINE);
		germanyRoute.setTerrainConforming(false, false);
		germanyRoute.getAttributes().setOpacity(0.8);
		Iterator<? extends LatLon> posis5 = germanyRoute.getLocations()
				.iterator();
		posis5.next();
		addRenderable(exportAnnotation(new Position(posis5.next(), 42000.0),
				"Deutschland: " + f2.format(exportGermany)));
		// attributesPlain.setInteriorOpacity(1);
		germanyRoute.getAttributes().setOutlineMaterial(Material.BLACK);
		germanyRoute.getAttributes().setDrawOutline(true);
		germanyRoute.getAttributes().setDrawInterior(true);

		addRenderable(germanyRoute);

		Route usaRoute = new Route();
		usaRoute.setAltitudes(40000.0, 42000.0);
		usaRoute.setWidth(exportUSA);

		usaRoute.setLocations(Arrays.asList(
				LatLon.fromDegrees(3.162456, -62.607425),
				// LatLon.fromDegrees(21.371244,-156.196291),
				LatLon.fromDegrees(34.161818, -86.689457)

		// LatLon.fromDegrees(44.0, -120.0),
		// LatLon.fromDegrees(43.0, -120.0)
				));
		// route2.setTerrainConforming(false, false);
		usaRoute.getAttributes().setMaterial(Material.YELLOW);
		usaRoute.getAttributes().setOutlineOpacity(1);
		// curtain.getAttributes().setOutlineWidth(10);
		// curtain.setPathType(AVKey.RHUMB_LINE);
		usaRoute.setTerrainConforming(false, false);
		usaRoute.getAttributes().setOpacity(0.8);
		Iterator<? extends LatLon> posis6 = usaRoute.getLocations().iterator();
		posis6.next();
		addRenderable(exportAnnotation(new Position(posis6.next(), 42000.0),
				"USA: " + f2.format(exportUSA)));
		// attributesPlain.setInteriorOpacity(1);
		usaRoute.getAttributes().setOutlineMaterial(Material.BLACK);
		usaRoute.getAttributes().setDrawOutline(true);
		usaRoute.getAttributes().setDrawInterior(true);

		addRenderable(usaRoute);

	}

	public GlobeAnnotation exportAnnotation(Position position, String str) {

		AnnotationAttributes defaultAttributes = new AnnotationAttributes();
		defaultAttributes.setCornerRadius(10);
		defaultAttributes.setInsets(new Insets(8, 8, 8, 8));
		defaultAttributes.setBackgroundColor(new Color(00, 00, 99));
		defaultAttributes.setTextColor(Color.WHITE);
		defaultAttributes.setDrawOffset(new Point(25, 25));
		defaultAttributes.setDistanceMinScale(.5);
		defaultAttributes.setDistanceMaxScale(2);
		defaultAttributes.setDistanceMinOpacity(.5);
		defaultAttributes.setLeaderGapWidth(14);
		defaultAttributes.setDrawOffset(new Point(20, 40));

		GlobeAnnotation ga2 = new GlobeAnnotation(str, position,
				Font.decode("Arial-BOLD-12"));
		ga2.getAttributes().setDefaults(defaultAttributes);

		return ga2;
	}

	// public AnnotationLayer addAnnotations(){
	//
	//
	//
	//
	// // Using a ScreenAnnotation
	// AnnotationAttributes defaultAttributes = new AnnotationAttributes();
	// defaultAttributes.setCornerRadius(10);
	// defaultAttributes.setInsets(new Insets(8, 8, 8, 8));
	// defaultAttributes.setBackgroundColor(new Color(00, 00, 99));
	// defaultAttributes.setTextColor(Color.WHITE);
	// defaultAttributes.setDrawOffset(new Point(25, 25));
	// defaultAttributes.setDistanceMinScale(.5);
	// defaultAttributes.setDistanceMaxScale(2);
	// defaultAttributes.setDistanceMinOpacity(.5);
	// defaultAttributes.setLeaderGapWidth(14);
	// defaultAttributes.setDrawOffset(new Point(20, 40));
	//
	// ScreenAnnotation sa = new
	// ScreenAnnotation("Gesamte- und Jährliche Abholzung des Regenwaldes in Pará, Brazilien",
	// new Point(900, 1000));
	// sa.getAttributes().setDefaults(defaultAttributes);
	// sa.getAttributes().setCornerRadius(0);
	// sa.getAttributes().setFont(Font.decode("Arial-BOLD-36"));
	// sa.getAttributes().setSize(new Dimension(1300, 0));
	// // sa.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use
	// strict dimension width - 200
	//
	// sa.getAttributes().setDrawOffset(new Point(100, 0)); // screen point is
	// annotation bottom left corner
	// sa.getAttributes().setHighlightScale(1); // No highlighting either
	// annotationLayer.addAnnotation(sa);
	//
	// ScreenAnnotation sa2 = new ScreenAnnotation(year, new Point(1750, 60));
	// sa2.getAttributes().setDefaults(defaultAttributes);
	// sa2.getAttributes().setCornerRadius(0);
	// sa2.getAttributes().setSize(new Dimension(50, 0));
	// sa2.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use strict
	// dimension width - 200
	// sa2.getAttributes().setDrawOffset(new Point(100, 0)); // screen point is
	// annotation bottom left corner
	// sa2.getAttributes().setHighlightScale(1); // No highlighting either
	// annotationLayer.addAnnotation(sa2);
	//
	// AnnotationAttributes defaultAttributes2 = new AnnotationAttributes();
	// defaultAttributes2.setBackgroundColor(new Color(0f, 0f, 0f, 0f));
	// defaultAttributes2.setTextColor(Color.WHITE);
	// defaultAttributes2.setLeaderGapWidth(14);
	// defaultAttributes2.setInsets(new Insets(8, 8, 8, 8));
	// // defaultAttributes2.setBackgroundColor(new Color(0f, 0f, 0f, .5f));
	//
	// defaultAttributes2.setCornerRadius(0);
	// defaultAttributes2.setSize(new Dimension(300, 0));
	// defaultAttributes2.setAdjustWidthToText(AVKey.SIZE_FIT_TEXT); // use
	// strict dimension width - 200
	// defaultAttributes2.setFont(Font.decode("Arial-BOLD-12"));
	// defaultAttributes2.setBorderWidth(0);
	// defaultAttributes2.setHighlightScale(1); // No highlighting either
	// defaultAttributes2.setCornerRadius(0);
	//
	// ScreenAnnotation legendHeader = new
	// ScreenAnnotation("Jährliche Abholzung des Regenwaldes in % (" + year
	// +")", new Point(1750, 125));
	// legendHeader.getAttributes().setDefaults(defaultAttributes2);
	// legendHeader.getAttributes().setBackgroundColor(new Color(0.2f, 0.2f,
	// 0.2f, .5f));
	// legendHeader.getAttributes().setCornerRadius(0);
	// legendHeader.getAttributes().setFont(Font.decode("Arial-BOLD-15"));
	// legendHeader.getAttributes().setSize(new Dimension(250, 170));
	// // legendHeader.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
	// // use strict dimension width - 200
	// // legendHeader.getAttributes().setDrawOffset(new Point(100, 0)); //
	// screen point is annotation bottom left corner
	// legendHeader.getAttributes().setHighlightScale(1); // No highlighting
	// either
	// // legend.getAttributes().setImageOffset(new Point(1, 1));
	// annotationLayer.addAnnotation(legendHeader);
	//
	// ScreenAnnotation legend = new ScreenAnnotation(">4%", new Point(1700,
	// 220));
	// legend.getAttributes().setDefaults(defaultAttributes2);
	// legend.getAttributes().setImageSource(redPic.getPowerOfTwoImage());
	// legend.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
	// legend.getAttributes().setCornerRadius(0);
	// // legend.getAttributes().setSize(new Dimension(50, 0));
	// legend.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use
	// strict dimension width - 200
	// legend.getAttributes().setDrawOffset(new Point(100, 0)); // screen point
	// is annotation bottom left corner
	// legend.getAttributes().setHighlightScale(1); // No highlighting either
	// // legend.getAttributes().setImageOffset(new Point(1, 1));
	// legend.getAttributes().setInsets(new Insets(0, 40, 0, 0));
	// annotationLayer.addAnnotation(legend);
	//
	// ScreenAnnotation legend2 = new ScreenAnnotation("2-4%", new Point(1700,
	// 190));
	// legend2.getAttributes().setDefaults(defaultAttributes2);
	// legend2.getAttributes().setImageSource(orangePic.getPowerOfTwoImage());
	// legend2.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
	// legend2.getAttributes().setCornerRadius(0);
	// // legend2.getAttributes().setSize(new Dimension(50, 0));
	// legend2.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use
	// strict dimension width - 200
	// legend2.getAttributes().setDrawOffset(new Point(100, 0)); // screen point
	// is annotation bottom left corner
	// legend2.getAttributes().setHighlightScale(1); // No highlighting either
	// legend2.getAttributes().setInsets(new Insets(0, 40, 0, 0));
	// annotationLayer.addAnnotation(legend2);
	//
	// ScreenAnnotation legend3 = new ScreenAnnotation("1-2%", new Point(1700,
	// 160));
	// legend3.getAttributes().setDefaults(defaultAttributes2);
	// legend3.getAttributes().setImageSource(yellowPic.getPowerOfTwoImage());
	// legend3.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
	// legend3.getAttributes().setCornerRadius(0);
	// // legend3.getAttributes().setSize(new Dimension(50, 0));
	// legend3.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use
	// strict dimension width - 200
	// legend3.getAttributes().setDrawOffset(new Point(100, 0)); // screen point
	// is annotation bottom left corner
	// legend3.getAttributes().setHighlightScale(1); // No highlighting either
	// legend3.getAttributes().setInsets(new Insets(0, 40, 0, 0));
	//
	// annotationLayer.addAnnotation(legend3);
	//
	// ScreenAnnotation legend4 = new ScreenAnnotation("<1%", new Point(1700,
	// 130));
	// legend4.getAttributes().setDefaults(defaultAttributes2);
	// legend4.getAttributes().setImageSource(greenPic.getPowerOfTwoImage());
	// legend4.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
	// legend4.getAttributes().setCornerRadius(0);
	// // legend4.getAttributes().setSize(new Dimension(50, 0));
	// legend4.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use
	// strict dimension width - 200
	// legend4.getAttributes().setDrawOffset(new Point(100, 0)); // screen point
	// is annotation bottom left corner
	// legend4.getAttributes().setHighlightScale(1); // No highlighting either
	// legend4.getAttributes().setInsets(new Insets(0, 40, 0, 0));
	//
	// annotationLayer.addAnnotation(legend4);
	//
	//
	// // GlobeAnnotation ga = new GlobeAnnotation(""+(int)(minpop*100)+"%",
	// (Position) posMin, Font.decode("Arial-BOLD-12"));
	// // ga.getAttributes().setDefaults(defaultAttributes);
	// //
	// // annotationLayer.addAnnotation(ga);
	//
	// GlobeAnnotation ga2 = new GlobeAnnotation(""+(int)(maxpop*100)+"%",
	// (Position) posMax, Font.decode("Arial-BOLD-12"));
	// ga2.getAttributes().setDefaults(defaultAttributes);
	//
	// // annotationLayer.addAnnotation(ga2);
	//
	// return annotationLayer;
	// }

	public AnnotationLayer addAnnotations() {

		annotationLayer.setName(year + " population");

		// Using a ScreenAnnotation
		AnnotationAttributes defaultAttributes = new AnnotationAttributes();
		defaultAttributes.setCornerRadius(10);
		defaultAttributes.setInsets(new Insets(8, 8, 8, 8));
		defaultAttributes.setBackgroundColor(new Color(00, 00, 99));
		defaultAttributes.setTextColor(Color.WHITE);
		defaultAttributes.setDrawOffset(new Point(25, 25));
		defaultAttributes.setDistanceMinScale(.5);
		defaultAttributes.setDistanceMaxScale(2);
		defaultAttributes.setDistanceMinOpacity(.5);
		defaultAttributes.setLeaderGapWidth(14);
		defaultAttributes.setDrawOffset(new Point(20, 40));

		ScreenAnnotation sa = new ScreenAnnotation(
				"Abholzung und Bodennutzung in Pará & Fleisch Exporte Brasiliens ",
				new Point(470, 700));
		// ScreenAnnotation sa = new
		// ScreenAnnotation("Deforestation and population in Pará, Brazilien",
		// new Point(470, 700));

		sa.getAttributes().setDefaults(defaultAttributes);
		sa.getAttributes().setCornerRadius(0);
		sa.getAttributes().setFont(Font.decode("Arial-BOLD-20"));
		sa.getAttributes().setSize(new Dimension(1300, 0));
		// sa.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use
		// strict dimension width - 200

		sa.getAttributes().setDrawOffset(new Point(100, 0)); // screen point is
																// annotation
																// bottom left
																// corner
		sa.getAttributes().setHighlightScale(1); // No highlighting either
		annotationLayer.addAnnotation(sa);

		ScreenAnnotation sa2 = new ScreenAnnotation("Jahr " + year, new Point(
				125, 550));
		// ScreenAnnotation sa2 = new ScreenAnnotation("Year "+year, new
		// Point(880, 600));

		sa2.getAttributes().setDefaults(defaultAttributes);
		sa2.getAttributes().setCornerRadius(0);
		sa2.getAttributes().setFont(Font.decode("Arial-BOLD-22"));
		// sa2.getAttributes().setSize(new Dimension(90, 0));
		sa2.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIT_TEXT); // use
																		// strict
																		// dimension
																		// width
																		// - 200
		// sa2.getAttributes().setDrawOffset(new Point(100, 0)); // screen point
		// is annotation bottom left corner
		sa2.getAttributes().setHighlightScale(1); // No highlighting either
		annotationLayer.addAnnotation(sa2);

		AnnotationAttributes defaultAttributes2 = new AnnotationAttributes();
		defaultAttributes2.setBackgroundColor(new Color(0f, 0f, 0f, 0f));
		defaultAttributes2.setTextColor(Color.WHITE);
		defaultAttributes2.setLeaderGapWidth(14);
		defaultAttributes2.setInsets(new Insets(8, 8, 8, 8));
		// defaultAttributes2.setBackgroundColor(new Color(0f, 0f, 0f, .5f));

		defaultAttributes2.setCornerRadius(0);
		defaultAttributes2.setSize(new Dimension(300, 0));
		defaultAttributes2.setAdjustWidthToText(AVKey.SIZE_FIT_TEXT); // use
																		// strict
																		// dimension
																		// width
																		// - 200
		defaultAttributes2.setFont(Font.decode("Arial-BOLD-12"));
		defaultAttributes2.setBorderWidth(0);
		defaultAttributes2.setHighlightScale(1); // No highlighting either
		defaultAttributes2.setCornerRadius(0);

		ScreenAnnotation arcLegend = new ScreenAnnotation(
				"Rindfleischexporte Brasiliens in Tonnen", new Point(920, 400));
		// arcLegend.getAttributes().setDefaults(defaultAttributes2);
		arcLegend.getAttributes().setTextColor(Color.WHITE);
		arcLegend.getAttributes().setBackgroundColor(
				new Color(0.2f, 0.2f, 0.2f, .5f));
		arcLegend.getAttributes().setImageSource(arcPic.getPowerOfTwoImage());
		arcLegend.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
		arcLegend.getAttributes().setImageScale(0.35);
		arcLegend.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		arcLegend.getAttributes().setInsets(new Insets(70, 8, 0, 0));
		arcLegend.getAttributes().setBorderColor(Color.BLACK);
		arcLegend.getAttributes().setBorderWidth(1);
		// legendHeader.getAttributes().
		arcLegend.getAttributes().setCornerRadius(0);
		arcLegend.getAttributes().setFont(Font.decode("Arial-BOLD-15"));
		arcLegend.getAttributes().setSize(new Dimension(190, 120));
		arcLegend.setAlwaysOnTop(false);
		this.addRenderable(arcLegend);

		ScreenAnnotation legendHeader = new ScreenAnnotation(
				"Abgeholzte Fläche", new Point(920, 225));
		// legendHeader.getAttributes().setDefaults(defaultAttributes2);
		legendHeader.getAttributes().setTextColor(Color.WHITE);
		legendHeader.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		legendHeader.getAttributes().setBackgroundColor(
				new Color(0.2f, 0.2f, 0.2f, .5f));
		legendHeader.getAttributes().setBorderColor(Color.BLACK);
		legendHeader.getAttributes().setBorderWidth(1);
		legendHeader.getAttributes().setInsets(new Insets(10, 10, 0, 0));
		// legendHeader.getAttributes().
		legendHeader.getAttributes().setCornerRadius(0);
		legendHeader.getAttributes().setFont(Font.decode("Arial-BOLD-15"));
		legendHeader.getAttributes().setSize(new Dimension(190, 150));
		legendHeader.setAlwaysOnTop(false);
		this.addRenderable(legendHeader);

		// legendHeader.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// legendHeader.getAttributes().setDrawOffset(new Point(100, 0)); //
		// screen point is annotation bottom left corner
		legendHeader.getAttributes().setHighlightScale(1); // No highlighting
															// either
		// legend.getAttributes().setImageOffset(new Point(1, 1));

		ScreenAnnotation legend = new ScreenAnnotation(
				"-genutzt für Landwirtschaft und Viehzucht",
				new Point(920, 285));
		legend.getAttributes().setDefaults(defaultAttributes2);
		legend.getAttributes().setImageSource(brownPic.getPowerOfTwoImage());
		legend.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
		legend.getAttributes().setCornerRadius(0);
		legend.getAttributes().setSize(new Dimension(180, 0));
		legend.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use
																		// strict
																		// dimension
																		// width
																		// - 200
		legend.getAttributes().setDrawOffset(new Point(0, 0)); // screen point
																// is annotation
																// bottom left
																// corner
		// legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// legend.getAttributes().setImageOffset(new Point(1, 1));
		legend.getAttributes().setInsets(new Insets(0, 50, 0, 0));
		legend.setAlwaysOnTop(true);

		annotationLayer.addAnnotation(legend);

		ScreenAnnotation legend2 = new ScreenAnnotation(
				"-genutzt für nicht landwirtschaftliche Zwecke", new Point(920,
						225));
		legend2.getAttributes().setDefaults(defaultAttributes2);
		legend2.getAttributes().setImageSource(yellowPic.getPowerOfTwoImage());
		legend2.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
		legend2.getAttributes().setCornerRadius(0);
		// legend2.getAttributes().setSize(new Dimension(50, 0));
		legend2.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use
																		// strict
																		// dimension
																		// width
																		// - 200
		legend2.getAttributes().setDrawOffset(new Point(0, 0)); // screen point
																// is annotation
																// bottom left
																// corner
		legend2.getAttributes().setHighlightScale(1); // No highlighting either
		legend2.getAttributes().setInsets(new Insets(0, 50, 0, 0));
		legend2.getAttributes().setSize(new Dimension(180, 0));

		annotationLayer.addAnnotation(legend2);

		// ScreenAnnotation ifgiLogo = new ScreenAnnotation("", new Point(966,
		// 700));
		// ifgiLogo.getAttributes().setDefaults(defaultAttributes2);
		// ifgiLogo.getAttributes().setImageSource(ifgiPic.getPowerOfTwoImage());
		// ifgiLogo.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
		// ifgiLogo.getAttributes().setImageScale(0.25);
		// ifgiLogo.getAttributes().setCornerRadius(0);
		// ifgiLogo.getAttributes().setSize(new Dimension(100, 40));
		// //ifgiLogo.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); //
		// use strict dimension width - 200
		// // ifgiLogo.getAttributes().setDrawOffset(new Point(100, 0)); //
		// screen point is annotation bottom left corner
		// ifgiLogo.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // ifgiLogo.getAttributes().setInsets(new Insets(0, 40, 0, 0));
		//
		// annotationLayer.addAnnotation(ifgiLogo);

		ScreenAnnotation heightLegend = new ScreenAnnotation(
				"Höhe entspricht der gesamten Abholzung in Prozent", new Point(
						920, 65));
		// ScreenAnnotation wwuLogo = new ScreenAnnotation("", new Point(950,
		// 730));
		heightLegend.getAttributes().setDefaults(defaultAttributes2);
		heightLegend.getAttributes().setFont(Font.decode("Arial-BOLD-15"));
		heightLegend.getAttributes().setImageSource(
				columnPic.getPowerOfTwoImage());
		heightLegend.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
		heightLegend.getAttributes().setImageScale(0.6);
		heightLegend.getAttributes().setCornerRadius(0);
		heightLegend.getAttributes().setSize(new Dimension(195, 130));
		heightLegend.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use
																				// strict
																				// dimension
																				// width
																				// -
																				// 200
		heightLegend.getAttributes().setBackgroundColor(
				new Color(0.2f, 0.2f, 0.2f, .5f));
		heightLegend.getAttributes().setBorderColor(Color.BLACK);
		heightLegend.getAttributes().setBorderWidth(1);
		heightLegend.getAttributes().setInsets(new Insets(0, 90, 0, 0));
		annotationLayer.addAnnotation(heightLegend);
		//
		//
		//
		// // // ifgiLogo.getAttributes().setDrawOffset(new Point(100, 0)); //
		// screen point is annotation bottom left corner
		// // wwuLogo.getAttributes().setHighlightScale(1); // No highlighting
		// either
		//
		//
		//
		// annotationLayer.addAnnotation(heightLegend);
		// GlobeAnnotation ga = new GlobeAnnotation(name, (Position) pos,
		// Font.decode("Arial-BOLD-12"));
		// ga.getAttributes().setDefaults(defaultAttributes);

		// annotationLayer.addAnnotation(ga);

		return annotationLayer;
	}

	public void addPictureLayer(Position position, PowerOfTwoPaddedImage pic,
			double scale) {

		GlobeAnnotation ga = new GlobeAnnotation("", position,
				Font.decode("Arial-BOLD-12"));
		ga.getAttributes().setImageSource(pic.getPowerOfTwoImage());
		ga.getAttributes().setBorderColor(Color.BLACK);
		ga.getAttributes().setBackgroundColor(Color.BLACK);
		ga.getAttributes().setBorderWidth(1);
		ga.getAttributes().setImageScale(scale);
		// ga.getAttributes().setSize(new Dimension(400,266));
		ga.setAlwaysOnTop(true);
		ga.setMaxActiveAltitude(1081941);
		annotationLayer.addAnnotation(ga);
	}

	public AnnotationLayer generalInformationLayer() {

		AnnotationAttributes defaultAttributes = new AnnotationAttributes();
		defaultAttributes.setCornerRadius(10);
		defaultAttributes.setInsets(new Insets(8, 8, 8, 8));
		defaultAttributes.setBackgroundColor(new Color(00, 00, 99));
		defaultAttributes.setTextColor(Color.WHITE);
		defaultAttributes.setDrawOffset(new Point(25, 25));
		defaultAttributes.setDistanceMinScale(.5);
		defaultAttributes.setDistanceMaxScale(2);
		defaultAttributes.setDistanceMinOpacity(.5);
		defaultAttributes.setLeaderGapWidth(14);
		defaultAttributes.setDrawOffset(new Point(20, 40));

		AnnotationAttributes defaultAttributes2 = new AnnotationAttributes();
		defaultAttributes2.setBackgroundColor(new Color(0f, 0f, 0f, 0f));
		defaultAttributes2.setTextColor(Color.WHITE);
		defaultAttributes2.setLeaderGapWidth(14);
		defaultAttributes2.setInsets(new Insets(8, 8, 8, 8));
		// defaultAttributes2.setBackgroundColor(new Color(0f, 0f, 0f, .5f));

		defaultAttributes2.setCornerRadius(0);
		defaultAttributes2.setSize(new Dimension(300, 0));
		defaultAttributes2.setAdjustWidthToText(AVKey.SIZE_FIXED); // use strict
																	// dimension
																	// width -
																	// 200
		defaultAttributes2.setFont(Font.decode("Arial-BOLD-16"));
		defaultAttributes2.setBorderWidth(0);
		defaultAttributes2.setHighlightScale(1); // No highlighting either
		defaultAttributes2.setCornerRadius(0);

		generalAnnotationLayer.setMinActiveAltitude(9000000.0);

		// RETRIEVE RELEVANT INFORMATION
		double averageGDP = 0;

		double population = 0;

		double averageDefor = 0;

		double averageAcumDefor = 0;

		double totalArea = 0;

		double totalExport = 0;

		double totalExport04 = 1184295;
		double totalExport05 = 1358679;
		double totalExport06 = 1610516;
		double totalExport07 = 1618671;
		double totalExport08 = 1384274;
		double cattleInventory = 0;

		for (int j = 0; j < muniData.size(); j++) {

			if (year == "2004") {
				population += muniData.get(j).getPop2004();
				totalExport = totalExport04;
			} else if (year == "2005") {
				population += muniData.get(j).getPop2005();
				totalExport = totalExport05;
				cattleInventory += muniData.get(j).getCattle05();
				// System.out.println(muniData.get(j).getCattle05() + " "
				// +cattleInventory);
			} else if (year == "2006") {
				population += muniData.get(j).getPop2006();
				totalExport = totalExport06;
			} else if (year == "2007") {
				population += muniData.get(j).getPop2007();
				totalExport = totalExport07;
			} else if (year == "2008") {
				population += muniData.get(j).getPop2008();
				totalExport = totalExport08;
			}

			if (year == "2004") {
				averageGDP += muniData.get(j).getGdp04();
			} else if (year == "2005") {
				averageGDP += muniData.get(j).getGdp05();
			} else if (year == "2006") {
				averageGDP += muniData.get(j).getGdp06();
			} else if (year == "2007") {
				averageGDP += muniData.get(j).getGdp07();
			} else if (year == "2008") {
				averageGDP += muniData.get(j).getGdp08();
			}

			if (year == "2004") {
				averageDefor += muniData.get(j).getTotalDefor2004()
						* muniData.get(j).getArea();
			} else if (year == "2005") {
				averageDefor += muniData.get(j).getTotalDefor2005()
						* muniData.get(j).getArea();
			} else if (year == "2006") {
				averageDefor += muniData.get(j).getTotalDefor2006()
						* muniData.get(j).getArea();
			} else if (year == "2007") {
				averageDefor += muniData.get(j).getTotalDefor2007()
						* muniData.get(j).getArea();
			} else if (year == "2008") {
				averageDefor += muniData.get(j).getTotalDefor2008()
						* muniData.get(j).getArea();
			}

			if (year == "2004") {
				averageAcumDefor += muniData.get(j).getTotalAcum2004()
						* muniData.get(j).getArea();
			} else if (year == "2005") {
				averageAcumDefor += muniData.get(j).getTotalAcum2005()
						* muniData.get(j).getArea();
			} else if (year == "2006") {
				averageAcumDefor += muniData.get(j).getTotalAcum2006()
						* muniData.get(j).getArea();
			} else if (year == "2007") {
				averageAcumDefor += muniData.get(j).getTotalAcum2007()
						* muniData.get(j).getArea();
			} else if (year == "2008") {
				averageAcumDefor += muniData.get(j).getTotalAcum2008()
						* muniData.get(j).getArea();
			}

			totalArea += muniData.get(j).getArea();

		}

		averageAcumDefor = averageAcumDefor / totalArea;
		averageDefor = averageDefor / totalArea;
		averageGDP = (averageGDP / population) * 1000;

		double totalDeforestArea = totalArea * averageAcumDefor;

		ScreenAnnotation factAnnotation = new ScreenAnnotation("", new Point(
				160, 380));
		// factAnnotation.getAttributes().setDefaults(defaultAttributes2);
		factAnnotation.getAttributes().setBackgroundColor(
				new Color(0.2f, 0.2f, 0.2f, .5f));
		// backgroundAnnotation.getAttributes().setOpacity(0.5);
		factAnnotation.getAttributes().setBorderColor(Color.BLACK);
		factAnnotation.getAttributes().setTextColor(Color.WHITE);
		factAnnotation.getAttributes().setBorderWidth(1);
		// backgroundAnnotation.getAttributes().
		factAnnotation.getAttributes().setCornerRadius(0);
		factAnnotation.getAttributes().setFont(Font.decode("Arial-BOLD-17"));
		factAnnotation.getAttributes().setSize(new Dimension(280, 0));
		factAnnotation.setAlwaysOnTop(false);
		factAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		generalAnnotationLayer.addAnnotation(factAnnotation);

		ScreenAnnotation statAnnotation = new ScreenAnnotation("", new Point(
				175, 210));
		statAnnotation.getAttributes().setDefaults(defaultAttributes2);
		statAnnotation.getAttributes().setBackgroundColor(
				new Color(0.2f, 0.2f, 0.2f, .5f));
		// backgroundAnnotation.getAttributes().setOpacity(0.5);
		statAnnotation.getAttributes().setBorderColor(Color.BLACK);
		statAnnotation.getAttributes().setBorderWidth(1);
		// backgroundAnnotation.getAttributes().
		statAnnotation.getAttributes().setCornerRadius(0);
		statAnnotation.getAttributes().setFont(Font.decode("Arial-BOLD-17"));
		// backgroundAnnotation.getAttributes().setSize(new Dimension(300,
		// 190));
		statAnnotation.setAlwaysOnTop(false);
		statAnnotation.getAttributes()
				.setAdjustWidthToText(AVKey.SIZE_FIT_TEXT);
		generalAnnotationLayer.addAnnotation(statAnnotation);

		if (year == "2002") {
			factAnnotation.setText("2002");
			statAnnotation.setText("Pará \n Fläche: "
					+ f2.format((int) totalArea)
					+ " km²\n Abgeholzte Fläche: ~"
					+ f2.format((int) totalDeforestArea)
					+ " km²\n\n Rindfleisch Export Brasiliens: "
					+ f2.format((int) totalExport) + " Tonnen");
		}
		if (year == "2003") {
			factAnnotation.setText("2003");
			statAnnotation.setText("Pará \n Fläche: "
					+ f2.format((int) totalArea)
					+ " km²\n Abgeholzte Fläche: ~"
					+ f2.format((int) totalDeforestArea)
					+ " km²\n\n Rindfleisch Export Brasiliens: "
					+ f2.format((int) totalExport) + " Tonnen");

		}
		if (year == "2004") {
			factAnnotation
					.setText("Die ersten Rinder kamen 1493 mit Kolumbus nach Südamerika.");
			statAnnotation.setText("Pará \n Fläche: "
					+ f2.format((int) totalArea)
					+ " km²\n Abgeholzte Fläche: ~"
					+ f2.format((int) totalDeforestArea)
					+ " km²\n\n Rindfleisch Export Brasiliens: "
					+ f2.format((int) totalExport) + " Tonnen");

		}
		if (year == "2005") {
			factAnnotation.setText("Im Jahr 2005 gab es "
					+ f2.format(cattleInventory) + " Rinder in Pará.");
			statAnnotation.setText("Pará \n Fläche: "
					+ f2.format((int) totalArea)
					+ " km²\n Abgeholzte Fläche: ~"
					+ f2.format((int) totalDeforestArea)
					+ " km²\n\n Rindfleisch Export Brasiliens: "
					+ f2.format((int) totalExport) + " Tonnen");

		}
		if (year == "2006") {
			factAnnotation
					.setText("Brasiliens Fleisch Exporte stiegen um 14% jährlich zwischen 2002 und 2007.");
			statAnnotation.setText("Pará \n Fläche: "
					+ f2.format((int) totalArea)
					+ " km²\n Abgeholzte Fläche: ~"
					+ f2.format((int) totalDeforestArea)
					+ " km²\n\n Rindfleisch Export Brasiliens: "
					+ f2.format((int) totalExport) + " Tonnen");

		}
		if (year == "2007") {
			factAnnotation
					.setText("Brasilien ist der größte Fleischexporteur der Welt, mit 34% Anteil am globalen Markt.");
			statAnnotation.setText("Pará \n Fläche: "
					+ f2.format((int) totalArea)
					+ " km²\n Abgeholzte Fläche: ~"
					+ f2.format((int) totalDeforestArea)
					+ " km²\n\n Rindfleisch Export Brasiliens: "
					+ f2.format((int) totalExport) + " Tonnen");

		}
		if (year == "2008") {
			factAnnotation
					.setText("37% der Beschäftigten Brasiliens arbeiten in der Landwirtschaft.");
			statAnnotation.setText("Pará \n Fläche: "
					+ f2.format((int) totalArea)
					+ " km²\n Abgeholzte Fläche: ~"
					+ f2.format((int) totalDeforestArea)
					+ " km²\n\n Rindfleisch Export Brasiliens: "
					+ f2.format((int) totalExport) + " Tonnen");

		}

		return generalAnnotationLayer;
	}

}
