package de.ifgi.worldwind.layer;

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
import gov.nasa.worldwindx.examples.util.PowerOfTwoPaddedImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;

import de.ifgi.data.MunicipalityDataItem;
/**
 * 
 * @author Umut Tas
 *
 */
public class AmazonGDPLayer extends RenderableLayer {

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

	ShapeAttributes sideAttributes5;
	ShapeAttributes sideHighlightAttributes5;
	ShapeAttributes capAttributes5;

	ShapeAttributes sideAttributes6;
	ShapeAttributes sideHighlightAttributes6;
	ShapeAttributes capAttributes6;

	ShapeAttributes sideAttributes7;
	ShapeAttributes sideHighlightAttributes7;
	ShapeAttributes capAttributes7;

	ArrayList<MunicipalityDataItem> muniData;
	AnnotationLayer annotationLayer = new AnnotationLayer();
	AnnotationLayer generalAnnotationLayer = new AnnotationLayer();
	String amazonRegion = "-45.3021476801,-1.42261406018;-45.3531185735,-1.73701446065;-45.1015508796,-1.36079972099;-45.079027782,-1.51917969123;-44.8162665717,-1.41872861919;-44.8982981162,-1.61413480287;-44.6430544844,-1.6246604203;-44.8463813436,-1.82910413191;-44.593924687,-1.74452766863;-44.4130981563,-2.4134617802;-44.0003495509,-2.4661982097;-44.0003516566,-6.75854771829;-44.6882870732,-7.39496275691;-45.4563112759,-7.67109510383;-45.9610830649,-8.819313121;-45.7841421121,-9.48054315858;-45.9556120402,-10.2188811388;-45.697845915,-10.2640041526;-46.617419071,-11.2898915038;-46.0869951119,-11.6225787368;-46.3152805806,-11.632694005;-46.3749057849,-11.8690420296;-46.1715870227,-11.9014299316;-46.3981876607,-12.0407842952;-46.1542937169,-12.483669162;-46.3051088764,-12.9503195756;-46.1146375956,-12.9184137053;-46.3641968185,-12.9916641854;-46.4179404133,-12.823918849;-47.4273055782,-13.2898839338;-47.6351111036,-13.1049408198;-47.679315453,-13.4681583234;-48.1657367334,-13.3062333759;-48.1469863656,-13.1524552525;-48.4420484414,-13.2928124189;-48.5092020949,-13.1292946252;-48.5867458672,-13.3180573061;-48.8578392476,-12.8058612284;-49.2377541839,-12.8844094915;-49.3698346117,-13.275040411;-50.2929920082,-12.8403562156;-50.1430199287,-12.3963691007;-50.3125341009,-12.4982157883;-50.8721389227,-13.7335313672;-51.0844318091,-14.9168480088;-51.652448159,-15.1803184574;-51.8799602581,-15.8258378273;-52.5258891802,-16.141000154;-52.6361136897,-16.5520778072;-53.0399823166,-16.9124016787;-53.2515721373,-17.6195234488;-53.0723500973,-18.0399166362;-53.9492813346,-17.9238698608;-53.706520834,-17.6628386101;-53.7090188381,-17.2286117569;-54.0850588857,-17.6194781535;-54.5820327937,-17.4684501131;-55.1276277636,-17.6531639631;-56.1134909587,-17.1676359383;-56.7342305817,-17.310300461;-57.0452932577,-17.7307603725;-57.4528493004,-17.9030818971;-57.7284377948,-17.5301858048;-58.3959128539,-17.1848934266;-58.3214638173,-16.2651852218;-60.1721468843,-16.2660372571;-60.2389929239,-15.4742811597;-60.5654061707,-15.1091120933;-60.2447051437,-15.0972371978;-60.4676735436,-13.7961583612;-61.0153204984,-13.4879625116;-61.8414998513,-13.5493386746;-62.1708944994,-13.1143464558;-62.7795199822,-13.0099941166;-63.1555618701,-12.6168629772;-64.2913574336,-12.5018197083;-65.0325260538,-11.9954877589;-65.3618461063,-11.2517599904;-65.395443891,-9.68617760199;-65.5608737534,-9.84506377006;-66.6205113862,-9.89443691824;-68.544026182,-11.11075613;-69.4172778708,-10.9278629035;-70.6231089286,-11.0004365849;-70.494761621,-9.42686853649;-71.2136171486,-9.96736448427;-72.2017761541,-10.0006265688;-72.2860407373,-9.54036572821;-73.2158486935,-9.41170073162;-72.9450028509,-8.98733046964;-73.9949173962,-7.53665090582;-73.9678681277,-7.34795764044;-73.7039833694,-7.30465887009;-73.7102578918,-6.83967107176;-73.141001005,-6.49808655334;-73.2400863408,-6.03231111378;-72.8753821087,-5.15626421986;-71.8846433156,-4.51645507742;-70.9444208846,-4.38618187546;-70.6547573511,-4.1275299335;-69.964873755,-4.30099631335;-69.3966171594,-1.1329907557;-69.6120517933,-0.513845353618;-70.0584842175,-0.187170353632;-70.044613692,0.558426416664;-69.1160101781,0.649441790603;-69.2662372282,1.06395811956;-69.8453796261,1.08497209949;-69.8435522488,1.72036316067;-68.1575191034,1.73123312018;-68.1836282504,1.97839397051;-67.9419758705,1.83033102092;-67.3898790395,2.24361268327;-67.0979718293,1.73220337777;-67.0887486944,1.16650132819;-66.8575033511,1.2298116062;-66.318980801,0.754645942371;-65.58604406,1.00853848143;-65.541014837,0.648396125024;-65.1035502092,1.15626062231;-64.3979064951,1.52644421253;-64.3384592114,1.36312957258;-63.9959529697,1.97950415344;-63.3727284775,2.21134781602;-63.4072596973,2.43554874916;-64.0565108869,2.49729178182;-64.1862358643,3.55923122929;-64.7973717083,4.28534598683;-64.1648505811,4.12589767326;-63.9645661065,3.86756765227;-63.2053815161,3.95089844447;-62.9606493164,3.60729253533;-62.4631430088,4.17756888389;-61.9311871701,4.10284284983;-61.3235972539,4.5345077534;-60.9967297855,4.51701534795;-60.5922787058,4.92646360181;-60.6975579113,5.22840601098;-60.1364388404,5.24868402873;-59.990043982,4.97007773147;-60.1644954948,4.50840531487;-59.6728261392,4.37284843882;-59.5167056687,3.94368789188;-59.8722731321,3.56283133551;-59.9711606969,2.59313313848;-59.7228229406,2.2764602225;-59.6905936587,1.75590877107;-58.8547591195,1.18311324388;-58.4966934272,1.26762369773;-58.3228356448,1.59668180992;-58.0046910922,1.50269593104;-57.9905555534,1.65811167504;-57.5380533182,1.70017400232;-57.30757027,1.99633854152;-55.9568104143,1.84478908905;-55.9035351737,2.04077512494;-56.1393099494,2.26544673099;-55.9709506631,2.52901967581;-55.3857535952,2.41806676682;-55.0041591626,2.59064131395;-54.4369310534,2.20949943067;-53.7675669885,2.37853703191;-52.9063377302,2.185134706;-52.5551873841,2.51458767864;-51.5143778303,4.43636939061;-51.0759495447,3.89023681067;-50.7028672631,2.13782101528;-50.446472785,2.19885493415;-50.2384729906,1.80279366582;-49.9153865636,1.69513109842;-50.0615842641,0.338036647172;-49.6779650015,0.365579321437;-48.9310370682,-0.226320114334;-48.4125844483,-0.257634310031;-48.4729248301,-0.49946420274;-47.9973014495,-0.705167450781;-47.704242146,-0.535651473402;-47.46949223,-0.766107854507;-47.3001876651,-0.599239380157;-46.9357659175,-0.876981075065;-46.6384270715,-0.788667572926;-46.6756563109,-0.976628676614;-46.4273137406,-0.858309004102;-46.4833701191,-1.03811057127;-46.3058720004,-1.08474989147;-46.2049783519,-0.886755698807;-46.2731428203,-1.17238590936;-46.171902149,-0.993238348974;-45.9487917804,-1.23902784433;-45.8417595625,-1.0450112044;-45.8014868537,-1.34483501793;-45.6799915574,-1.13868179795;-45.7260313942,-1.41776196157;-45.5804804357,-1.25765802432;-45.5192300755,-1.41249596103;-45.402468808,-1.30810234778;-45.4814078461,-1.53736722794;-45.3021476801,-1.42261406018";
	DecimalFormat f = new DecimalFormat(",##0.00");
	DecimalFormat f2 = new DecimalFormat(",##0");
	String name;
	LatLon pos;
	String year = "";

	private final static PowerOfTwoPaddedImage heightPic = PowerOfTwoPaddedImage
			.fromPath("images/height2.png");
	private final static PowerOfTwoPaddedImage colorSchemePic = PowerOfTwoPaddedImage
			.fromPath("images/farbverlauf3.jpg");

	private final static PowerOfTwoPaddedImage badPic = PowerOfTwoPaddedImage
			.fromPath("images/Amazonie_deforestation.jpg");
	private final static PowerOfTwoPaddedImage trees = PowerOfTwoPaddedImage
			.fromPath("images/bäume2.jpg");

	public AmazonGDPLayer(ArrayList<MunicipalityDataItem> muniData, String year) {
		super();
		this.year = year;
		this.muniData = muniData;
		setAttributes();
		drawPolygons();

	}

	public void setAttributes() {

		Material green = new Material(new Color(55, 219, 0));

		sideAttributes = new BasicShapeAttributes();
		sideAttributes.setInteriorMaterial(green);
		sideAttributes.setOutlineOpacity(0.5);
		sideAttributes.setInteriorOpacity(0.5);
		sideAttributes.setOutlineMaterial(green);
		sideAttributes.setOutlineWidth(2);
		sideAttributes.setDrawOutline(true);
		sideAttributes.setDrawInterior(true);
		sideAttributes.setEnableLighting(true);

		sideHighlightAttributes = new BasicShapeAttributes(sideAttributes);
		sideHighlightAttributes.setOutlineMaterial(Material.WHITE);
		sideHighlightAttributes.setOutlineOpacity(1);

		capAttributes = new BasicShapeAttributes(sideAttributes);
		capAttributes.setInteriorMaterial(green);
		capAttributes.setInteriorOpacity(0.8);
		// capAttributes.setDrawInterior(true);
		capAttributes.setEnableLighting(false);

		Material yellow = new Material(new Color(255, 255, 0));

		sideAttributes2 = new BasicShapeAttributes();
		sideAttributes2.setInteriorMaterial(yellow);
		sideAttributes2.setOutlineOpacity(0.5);
		sideAttributes2.setInteriorOpacity(0.5);
		sideAttributes2.setOutlineMaterial(yellow);
		sideAttributes2.setOutlineWidth(2);
		sideAttributes2.setDrawOutline(true);
		sideAttributes2.setDrawInterior(true);
		sideAttributes2.setEnableLighting(true);

		sideHighlightAttributes2 = new BasicShapeAttributes(sideAttributes2);
		sideHighlightAttributes2.setOutlineMaterial(Material.WHITE);
		sideHighlightAttributes2.setOutlineOpacity(1);

		capAttributes2 = new BasicShapeAttributes(sideAttributes2);
		capAttributes2.setInteriorMaterial(yellow);
		capAttributes2.setInteriorOpacity(0.8);
		capAttributes2.setDrawInterior(true);
		capAttributes2.setEnableLighting(false);

		Material darkYellow = new Material(new Color(255, 220, 0));
		sideAttributes3 = new BasicShapeAttributes();
		sideAttributes3.setInteriorMaterial(darkYellow);
		sideAttributes3.setOutlineOpacity(0.5);
		sideAttributes3.setInteriorOpacity(0.5);
		sideAttributes3.setOutlineMaterial(darkYellow);
		sideAttributes3.setOutlineWidth(2);
		sideAttributes3.setDrawOutline(true);
		sideAttributes3.setDrawInterior(true);
		sideAttributes3.setEnableLighting(true);

		sideHighlightAttributes3 = new BasicShapeAttributes(sideAttributes3);
		sideHighlightAttributes3.setOutlineMaterial(Material.WHITE);
		sideHighlightAttributes3.setOutlineOpacity(1);

		capAttributes3 = new BasicShapeAttributes(sideAttributes3);
		capAttributes3.setInteriorMaterial(darkYellow);
		capAttributes3.setInteriorOpacity(0.8);
		capAttributes3.setDrawInterior(true);
		capAttributes3.setEnableLighting(false);

		Material orange = new Material(new Color(255, 150, 0));
		sideAttributes4 = new BasicShapeAttributes();
		sideAttributes4.setInteriorMaterial(orange);
		sideAttributes4.setOutlineOpacity(0.5);
		sideAttributes4.setInteriorOpacity(0.5);
		sideAttributes4.setOutlineMaterial(orange);
		sideAttributes4.setOutlineWidth(2);
		sideAttributes4.setDrawOutline(true);
		sideAttributes4.setDrawInterior(true);
		sideAttributes4.setEnableLighting(true);

		sideHighlightAttributes4 = new BasicShapeAttributes(sideAttributes4);
		sideHighlightAttributes4.setOutlineMaterial(Material.WHITE);
		sideHighlightAttributes4.setOutlineOpacity(1);

		capAttributes4 = new BasicShapeAttributes(sideAttributes4);
		capAttributes4.setInteriorMaterial(orange);
		capAttributes4.setInteriorOpacity(0.8);
		capAttributes4.setDrawInterior(true);
		capAttributes4.setEnableLighting(false);

		Material red = new Material(new Color(255, 40, 0));
		sideAttributes5 = new BasicShapeAttributes();
		sideAttributes5.setInteriorMaterial(red);
		sideAttributes5.setOutlineOpacity(0.5);
		sideAttributes5.setInteriorOpacity(0.5);
		sideAttributes5.setOutlineMaterial(red);
		sideAttributes5.setOutlineWidth(2);
		sideAttributes5.setDrawOutline(true);
		sideAttributes5.setDrawInterior(true);
		sideAttributes5.setEnableLighting(true);

		sideHighlightAttributes5 = new BasicShapeAttributes(sideAttributes5);
		sideHighlightAttributes5.setOutlineMaterial(red);
		sideHighlightAttributes5.setOutlineOpacity(1);

		capAttributes5 = new BasicShapeAttributes(sideAttributes5);
		capAttributes5.setInteriorMaterial(red);
		capAttributes5.setInteriorOpacity(0.8);
		capAttributes5.setDrawInterior(true);
		capAttributes5.setEnableLighting(false);

		Material darkRed = new Material(new Color(180, 27, 0));
		sideAttributes6 = new BasicShapeAttributes();
		sideAttributes6.setInteriorMaterial(darkRed);
		sideAttributes6.setOutlineOpacity(0.5);
		sideAttributes6.setInteriorOpacity(0.5);
		sideAttributes6.setOutlineMaterial(darkRed);
		sideAttributes6.setOutlineWidth(2);
		sideAttributes6.setDrawOutline(true);
		sideAttributes6.setDrawInterior(true);
		sideAttributes6.setEnableLighting(true);

		sideHighlightAttributes6 = new BasicShapeAttributes(sideAttributes6);
		sideHighlightAttributes6.setOutlineMaterial(darkRed);
		sideHighlightAttributes6.setOutlineOpacity(1);

		capAttributes6 = new BasicShapeAttributes(sideAttributes6);
		capAttributes6.setInteriorMaterial(darkRed);
		capAttributes6.setInteriorOpacity(0.8);
		capAttributes6.setDrawInterior(true);
		capAttributes6.setEnableLighting(false);

		sideAttributes7 = new BasicShapeAttributes();
		sideAttributes7.setInteriorMaterial(Material.BLACK);
		sideAttributes7.setOutlineOpacity(0.5);
		sideAttributes7.setInteriorOpacity(0.5);
		sideAttributes7.setOutlineMaterial(Material.BLACK);
		sideAttributes7.setOutlineWidth(2);
		sideAttributes7.setDrawOutline(true);
		sideAttributes7.setDrawInterior(true);
		sideAttributes7.setEnableLighting(true);

		sideHighlightAttributes7 = new BasicShapeAttributes(sideAttributes7);
		sideHighlightAttributes7.setOutlineMaterial(Material.BLACK);
		sideHighlightAttributes7.setOutlineOpacity(1);

		capAttributes7 = new BasicShapeAttributes(sideAttributes7);
		capAttributes7.setInteriorMaterial(Material.BLACK);
		capAttributes7.setInteriorOpacity(0.8);
		capAttributes7.setDrawInterior(true);
		capAttributes7.setEnableLighting(false);
	}

	public void drawPolygons() {
		ExtrudedPolygon pgon;

		// double maxDefor=0;
		// double minDefor=2000000;
		double minPos = 0;
		double tempPop;
		double maxPos = 0;
		double minpop = 100000000;
		double maxpop = 0;

		for (int j = 0; j < muniData.size(); j++) {

			tempPop = muniData.get(j).getGdp04() / muniData.get(j).getPop2004();

			if (tempPop > maxpop) {
				// System.out.println(muniData.get(j).getName() + " "+
				// (int)(muniData.get(j).getGdp04()/muniData.get(j).getPop2004()));
				maxpop = tempPop;
				maxPos = j;
			}
			if (tempPop < minpop) {
				minpop = tempPop;
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
		ga.getAttributes().setBackgroundColor(new Color(0.2f, 0.2f, 0.2f, .5f));
		ga.getAttributes().setTextColor(Color.WHITE);
		ga.getAttributes().setBorderColor(Color.BLACK);

		annotationLayer.addAnnotation(ga);

		// Create a path, set some of its properties and set its attributes.
		for (int i = 0; i < muniData.size(); i++) {
			double totalAcumDefor = 0;
			if (year == "2002") {
				// totalAcumDefor = muniData.get(i).getTotalAcum2002();
				totalAcumDefor = muniData.get(i).getTotalDefor2002();

			}
			if (year == "2003") {
				// totalAcumDefor = muniData.get(i).getTotalAcum2003();
				totalAcumDefor = muniData.get(i).getTotalDefor2003();

			}
			if (year == "2004") {
				// totalAcumDefor = muniData.get(i).getTotalAcum2004();
				totalAcumDefor = muniData.get(i).getTotalDefor2004();

			}
			if (year == "2005") {
				// totalAcumDefor = muniData.get(i).getTotalAcum2005();
				totalAcumDefor = muniData.get(i).getTotalDefor2005();

			}
			if (year == "2006") {
				// totalAcumDefor = muniData.get(i).getTotalAcum2006();
				totalAcumDefor = muniData.get(i).getTotalDefor2006();

			}
			if (year == "2007") {
				// totalAcumDefor = muniData.get(i).getTotalAcum2007();
				totalAcumDefor = muniData.get(i).getTotalDefor2007();

			}
			if (year == "2008") {
				// totalAcumDefor = muniData.get(i).getTotalAcum2008();
				totalAcumDefor = muniData.get(i).getTotalDefor2008();

			}
			String[] borderStringArray = muniData.get(i).getBorder().split(";");

			double gdp = 0;
			double pop = 0;

			if (year == "2004") {
				gdp = muniData.get(i).getGdp04();
				pop = muniData.get(i).getPop2004();
			}
			if (year == "2005") {
				gdp = muniData.get(i).getGdp05();
				pop = muniData.get(i).getPop2005();
			}
			if (year == "2006") {
				gdp = muniData.get(i).getGdp06();
				pop = muniData.get(i).getPop2006();
			}
			if (year == "2007") {
				gdp = muniData.get(i).getGdp07();
				pop = muniData.get(i).getPop2007();
			}
			if (year.equals("2008")) {
				gdp = muniData.get(i).getGdp08();
				pop = muniData.get(i).getPop2008();
			}
			ArrayList<Position> borderPositions = new ArrayList<Position>();

			for (String str : borderStringArray) {
				String[] splitStr = str.split(",");

				borderPositions.add(Position.fromDegrees(
						Double.parseDouble(splitStr[1]),
						Double.parseDouble(splitStr[0]), 8000 * gdp / pop));

			}
			pgon = new ExtrudedPolygon(borderPositions);
			pos = pgon.getReferenceLocation();
			name = muniData.get(i).getUri();
			pgon.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
			pgon.setHighlighted(false);

			Material mat;

			if (totalAcumDefor != 0 && Math.abs(Math.log(totalAcumDefor)) < 10) {

				mat = new Material(new Color(255, Math.abs((int) ((Math
						.log(totalAcumDefor)) * 255 / (10))), 0));
			} else {
				mat = new Material(new Color(0, 255, 0));
			}

			ShapeAttributes side = new BasicShapeAttributes();
			side.setInteriorMaterial(mat);
			side.setOutlineOpacity(0.5);
			side.setInteriorOpacity(0.5);
			side.setOutlineMaterial(mat);
			side.setOutlineWidth(2);
			side.setDrawOutline(true);
			side.setDrawInterior(true);
			side.setEnableLighting(true);

			ShapeAttributes cap = new BasicShapeAttributes(sideAttributes);
			cap.setInteriorMaterial(mat);
			cap.setOutlineMaterial(Material.BLACK);
			cap.setInteriorOpacity(0.8);
			cap.setEnableLighting(false);

			pgon.setCapAttributes(cap);
			pgon.setSideAttributes(side);

			String[] splitStr = muniData.get(i).getCentroid().split(",");

			if (muniData.get(i).getName().equals("Santarém")
					|| muniData.get(i).getName().contains("Belém")
					|| muniData.get(i).getName().contains("Altamira")
					|| i == maxPos) {
				GlobeAnnotation ga2 = new GlobeAnnotation(muniData.get(i)
						.getName()
						+ "\nBIP/Person: "
						+ f2.format(gdp * 1000 / pop * 0.400125419) + "€",
						Position.fromDegrees(Double.parseDouble(splitStr[1]),
								Double.parseDouble(splitStr[0]), 8000 * gdp
										/ pop), Font.decode("Arial-BOLD-12"));
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
			addRenderable(pgon);
			if (i == maxPos) {
				addPictureLayer(Position.fromDegrees(
						Double.parseDouble(splitStr[1]),
						Double.parseDouble(splitStr[0]), 8000 * gdp / pop),
						badPic, 0.5);
			} else if (i == minPos) {
				addPictureLayer(Position.fromDegrees(
						Double.parseDouble(splitStr[1]),
						Double.parseDouble(splitStr[0]), 8000 * gdp / pop),
						trees, 0.2);

			}
		}
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
		ga.getAttributes().setSize(new Dimension(400, 266));
		ga.setAlwaysOnTop(true);
		ga.setMaxActiveAltitude(1081941);
		annotationLayer.addAnnotation(ga);
	}

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
				"Bruttoinlandsprodukt und jährliche Abholzungsrate Pará, Brasilien",
				new Point(470, 700));
		sa.getAttributes().setDefaults(defaultAttributes);
		sa.getAttributes().setCornerRadius(0);
		sa.getAttributes().setFont(Font.decode("Arial-BOLD-22"));
		sa.getAttributes().setSize(new Dimension(1300, 0));

		sa.getAttributes().setDrawOffset(new Point(100, 0)); // screen point is
																// annotation
																// bottom left
																// corner
		sa.getAttributes().setHighlightScale(1); // No highlighting either
		annotationLayer.addAnnotation(sa);

		ScreenAnnotation sa2 = new ScreenAnnotation("Jahr " + year, new Point(
				125, 550));

		sa2.getAttributes().setDefaults(defaultAttributes);
		sa2.getAttributes().setCornerRadius(0);
		sa2.getAttributes().setFont(Font.decode("Arial-BOLD-22"));
		sa2.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIT_TEXT); // use
																		// strict
																		// dimension
																		// width
																		// - 200
		sa2.getAttributes().setHighlightScale(1); // No highlighting either
		annotationLayer.addAnnotation(sa2);

		AnnotationAttributes defaultAttributes2 = new AnnotationAttributes();
		defaultAttributes2.setBackgroundColor(new Color(0f, 0f, 0f, 0f));
		defaultAttributes2.setTextColor(Color.WHITE);
		defaultAttributes2.setLeaderGapWidth(14);
		defaultAttributes2.setInsets(new Insets(8, 8, 8, 8));

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

		ScreenAnnotation legendHeader = new ScreenAnnotation(
				"Jährliche Abholzungsrate \n \n -hohe Abholzung \n\n\n \n \n \n\n   -geringe Abholzung",
				new Point(920, 270));
		legendHeader.getAttributes().setDefaults(defaultAttributes2);
		legendHeader.getAttributes().setBackgroundColor(
				new Color(0.2f, 0.2f, 0.2f, .5f));
		legendHeader.getAttributes().setImageSource(
				colorSchemePic.getPowerOfTwoImage());
		legendHeader.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
		legendHeader.getAttributes().setImageScale(0.75);
		legendHeader.getAttributes().setImageOffset(new Point(2, 60));
		legendHeader.getAttributes().setInsets(new Insets(6, 35, 6, 6));

		legendHeader.getAttributes().setBorderColor(Color.BLACK);
		legendHeader.getAttributes().setBorderWidth(1);
		legendHeader.getAttributes().setCornerRadius(0);
		legendHeader.getAttributes().setFont(Font.decode("Arial-BOLD-15"));
		legendHeader.getAttributes().setSize(new Dimension(200, 240));
		legendHeader.setAlwaysOnTop(false);
		this.addRenderable(legendHeader);

		legendHeader.getAttributes().setHighlightScale(1); // No highlighting
															// either

		ScreenAnnotation heightLegend = new ScreenAnnotation(
				"Höhe entspricht dem Bruttoinlands- produkt pro Person",
				new Point(920, 65));
		heightLegend.getAttributes().setDefaults(defaultAttributes2);
		heightLegend.getAttributes().setFont(Font.decode("Arial-BOLD-15"));
		heightLegend.getAttributes().setImageSource(
				heightPic.getPowerOfTwoImage());
		heightLegend.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
		heightLegend.getAttributes().setImageScale(0.5);
		heightLegend.getAttributes().setCornerRadius(0);
		heightLegend.getAttributes().setSize(new Dimension(180, 180));
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

		heightLegend.getAttributes().setInsets(new Insets(0, 70, 0, 0));

		annotationLayer.addAnnotation(heightLegend);

		return annotationLayer;
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

		for (int j = 0; j < muniData.size(); j++) {

			if (year == "2004") {
				population += muniData.get(j).getPop2004();
			} else if (year == "2005") {
				population += muniData.get(j).getPop2005();
			} else if (year == "2006") {
				population += muniData.get(j).getPop2006();
			} else if (year == "2007") {
				population += muniData.get(j).getPop2007();
			} else if (year == "2008") {
				population += muniData.get(j).getPop2008();
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
		averageGDP = (averageGDP * 1000 / population);

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

		// ScreenAnnotation populationAnnotation = new
		// ScreenAnnotation("Bevölkerung: "+ population, new Point(30,190));
		// populationAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// populationAnnotation.getAttributes().setCornerRadius(0);
		// // legend.getAttributes().setSize(new Dimension(50, 0));
		// populationAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// populationAnnotation.getAttributes().setDrawOffset(new Point(100,
		// 0)); // screen point is annotation bottom left corner
		// // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // legend.getAttributes().setImageOffset(new Point(1, 1));
		// populationAnnotation.getAttributes().setInsets(new Insets(0, 40, 0,
		// 0));
		// populationAnnotation.setAlwaysOnTop(true);
		// generalAnnotationLayer.addAnnotation(populationAnnotation);
		//
		// ScreenAnnotation totalDeforestationAnnotation = new
		// ScreenAnnotation("Gesamte Abholzung: "+
		// f.format(averageAcumDefor*100) +"%", new Point(30, 155));
		// totalDeforestationAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// totalDeforestationAnnotation.getAttributes().setCornerRadius(0);
		// // legend.getAttributes().setSize(new Dimension(50, 0));
		// totalDeforestationAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// totalDeforestationAnnotation.getAttributes().setDrawOffset(new
		// Point(100, 0)); // screen point is annotation bottom left corner
		// // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // legend.getAttributes().setImageOffset(new Point(1, 1));
		// totalDeforestationAnnotation.getAttributes().setInsets(new Insets(0,
		// 40, 0, 0));
		// totalDeforestationAnnotation.setAlwaysOnTop(true);
		// generalAnnotationLayer.addAnnotation(totalDeforestationAnnotation);
		//
		// ScreenAnnotation yearlyDeforestationAnnotation = new
		// ScreenAnnotation("Jährliche Abholzungsrate: "+
		// f.format(averageDefor*100) +"%", new Point(30, 120));
		// yearlyDeforestationAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// yearlyDeforestationAnnotation.getAttributes().setCornerRadius(0);
		// // legend.getAttributes().setSize(new Dimension(50, 0));
		// yearlyDeforestationAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// yearlyDeforestationAnnotation.getAttributes().setDrawOffset(new
		// Point(100, 0)); // screen point is annotation bottom left corner
		// // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // legend.getAttributes().setImageOffset(new Point(1, 1));
		// yearlyDeforestationAnnotation.getAttributes().setInsets(new Insets(0,
		// 40, 0, 0));
		// yearlyDeforestationAnnotation.setAlwaysOnTop(true);
		// generalAnnotationLayer.addAnnotation(yearlyDeforestationAnnotation);
		//
		// ScreenAnnotation gdpAnnotation = new
		// ScreenAnnotation("Bruttoinlandsprodukt: "+ f.format(averageGDP)
		// +"R$", new Point(30, 85));
		// gdpAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// gdpAnnotation.getAttributes().setCornerRadius(0);
		// // legend.getAttributes().setSize(new Dimension(50, 0));
		// gdpAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// gdpAnnotation.getAttributes().setDrawOffset(new Point(100, 0)); //
		// screen point is annotation bottom left corner
		// // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // legend.getAttributes().setImageOffset(new Point(1, 1));
		// gdpAnnotation.getAttributes().setInsets(new Insets(0, 40, 0, 0));
		// gdpAnnotation.setAlwaysOnTop(true);
		// generalAnnotationLayer.addAnnotation(gdpAnnotation);

		if (year == "2002") {
			factAnnotation.setText("2002");
			statAnnotation.setText("Pará \n Bevölkerung: "
					+ f2.format((int) population) + "\n Jährliche Abholzung: "
					+ f.format(averageDefor * 100)
					+ "%\nJährlich abgeholzte Fläche: "
					+ f.format(averageDefor * totalArea)
					+ "km²\n Bruttoinlandsprodukt: "
					+ f.format(averageGDP * 0.400125419) + "€");
		}
		if (year == "2003") {
			factAnnotation.setText("2003");
			statAnnotation.setText("Pará \n Bevölkerung: "
					+ f2.format((int) population) + "\n Jährliche Abholzung: "
					+ f.format(averageDefor * 100)
					+ "%\nJährlich abgeholzte Fläche: "
					+ f.format(averageDefor * totalArea)
					+ "km²\n Bruttoinlandsprodukt: "
					+ f.format(averageGDP * 0.400125419) + "€");

		}
		if (year == "2004") {
			factAnnotation.setText("Die jährliche Abholzung von "
					+ f.format(averageDefor * totalArea) + "km² entspricht "
					+ f.format(averageDefor * totalArea / 2569.69)
					+ " mal der Fläche des Saarlandes.");
			statAnnotation.setText("Pará \n Bevölkerung: "
					+ f2.format((int) population) + "\n Jährliche Abholzung: "
					+ f.format(averageDefor * 100)
					+ "%\nJährlich abgeholzte Fläche: "
					+ f.format(averageDefor * totalArea)
					+ "km²\n Bruttoinlandsprodukt: "
					+ f.format(averageGDP * 0.400125419) + "€");

		}
		if (year == "2005") {
			factAnnotation.setText("Die jährliche Abholzung von "
					+ f.format(averageDefor * totalArea) + "km² entspricht "
					+ f.format(averageDefor * totalArea / 2569.69)
					+ " mal der Fläche des Saarlandes.");
			statAnnotation.setText("Pará \n Bevölkerung: "
					+ f2.format((int) population) + "\n Jährliche Abholzung: "
					+ f.format(averageDefor * 100)
					+ "%\nJährlich abgeholzte Fläche: "
					+ f.format(averageDefor * totalArea)
					+ "km²\n Bruttoinlandsprodukt: "
					+ f.format(averageGDP * 0.400125419) + "€");

		}
		if (year == "2006") {
			factAnnotation.setText("Die jährliche Abholzung von "
					+ f.format(averageDefor * totalArea) + "km² entspricht "
					+ f.format(averageDefor * totalArea / 2569.69)
					+ " mal der Fläche des Saarlandes.");
			statAnnotation.setText("Pará \n Bevölkerung: "
					+ f2.format((int) population) + "\n Jährliche Abholzung: "
					+ f.format(averageDefor * 100)
					+ "%\nJährlich abgeholzte Fläche: "
					+ f.format(averageDefor * totalArea)
					+ "km²\n Bruttoinlandsprodukt: "
					+ f.format(averageGDP * 0.400125419) + "€");

		}
		if (year == "2007") {
			factAnnotation.setText("Die jährliche Abholzung von "
					+ f.format(averageDefor * totalArea) + "km² entspricht "
					+ f.format(averageDefor * totalArea / 2569.69)
					+ " mal der Fläche des Saarlandes.");
			statAnnotation.setText("Pará \n Bevölkerung: "
					+ f2.format((int) population) + "\n Jährliche Abholzung: "
					+ f.format(averageDefor * 100)
					+ "%\nJährlich abgeholzte Fläche: "
					+ f.format(averageDefor * totalArea)
					+ "km²\n Bruttoinlandsprodukt: "
					+ f.format(averageGDP * 0.400125419) + "€");

		}
		if (year == "2008") {
			factAnnotation
					.setText("Brasilien nutzt 16000 km² ("
							+ (f.format(16000 / 2569.69))
							+ "x Saarland) um Soja für Deutschlands Bedürfnisse anzubauen.");
			statAnnotation.setText("Pará \n Bevölkerung: "
					+ f2.format((int) population) + "\n Jährliche Abholzung: "
					+ f.format(averageDefor * 100)
					+ "%\nJährlich abgeholzte Fläche: "
					+ f.format(averageDefor * totalArea)
					+ "km²\n Bruttoinlandsprodukt: "
					+ f.format(averageGDP * 0.400125419) + "€");

		}

		// ScreenAnnotation backgroundAnnotation = new
		// ScreenAnnotation("Fakten über Para", new Point(165, 80));
		// backgroundAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// backgroundAnnotation.getAttributes().setBackgroundColor(new
		// Color(0.2f, 0.2f, 0.2f, .5f));
		// // backgroundAnnotation.getAttributes().setOpacity(0.5);
		// backgroundAnnotation.getAttributes().setBorderColor(Color.BLACK);
		// backgroundAnnotation.getAttributes().setBorderWidth(1);
		// // backgroundAnnotation.getAttributes().
		// backgroundAnnotation.getAttributes().setCornerRadius(0);
		// backgroundAnnotation.getAttributes().setFont(Font.decode("Arial-BOLD-17"));
		// backgroundAnnotation.getAttributes().setSize(new Dimension(300,
		// 190));
		// backgroundAnnotation.setAlwaysOnTop(false);
		// generalAnnotationLayer.addAnnotation(backgroundAnnotation);
		//
		// // ScreenAnnotation factAnnotation = new
		// ScreenAnnotation("Fakten über Para:", new Point(30, 425));
		// // factAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// // factAnnotation.getAttributes().setCornerRadius(0);
		// //// factAnnotation.getAttributes().setSize(new Dimension(50, 0));
		// //
		// factAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// // factAnnotation.getAttributes().setDrawOffset(new Point(100, 0));
		// // screen point is annotation bottom left corner
		// // // factAnnotation.getAttributes().setHighlightScale(1); // No
		// highlighting either
		// // // factAnnotation.getAttributes().setImageOffset(new Point(1, 1));
		// // factAnnotation.getAttributes().setInsets(new Insets(0, 40, 0, 0));
		// // factAnnotation.setAlwaysOnTop(true);
		// //
		// // generalAnnotationLayer.addAnnotation(factAnnotation);
		//
		// // ScreenAnnotation populationAnnotation = new
		// ScreenAnnotation("Bevölkerung: "+ population, new Point(30,190));
		// //
		// populationAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// // populationAnnotation.getAttributes().setCornerRadius(0);
		// //// legend.getAttributes().setSize(new Dimension(50, 0));
		// //
		// populationAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// // populationAnnotation.getAttributes().setDrawOffset(new Point(100,
		// 0)); // screen point is annotation bottom left corner
		// // // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // // legend.getAttributes().setImageOffset(new Point(1, 1));
		// // populationAnnotation.getAttributes().setInsets(new Insets(0, 40,
		// 0, 0));
		// // populationAnnotation.setAlwaysOnTop(true);
		// // generalAnnotationLayer.addAnnotation(populationAnnotation);
		// //
		// // ScreenAnnotation totalDeforestationAnnotation = new
		// ScreenAnnotation("Gesamte Abholzung: "+
		// f.format(averageAcumDefor*100) +"%", new Point(30, 155));
		// //
		// totalDeforestationAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// // totalDeforestationAnnotation.getAttributes().setCornerRadius(0);
		// //// legend.getAttributes().setSize(new Dimension(50, 0));
		// //
		// totalDeforestationAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// // totalDeforestationAnnotation.getAttributes().setDrawOffset(new
		// Point(100, 0)); // screen point is annotation bottom left corner
		// // // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // // legend.getAttributes().setImageOffset(new Point(1, 1));
		// // totalDeforestationAnnotation.getAttributes().setInsets(new
		// Insets(0, 40, 0, 0));
		// // totalDeforestationAnnotation.setAlwaysOnTop(true);
		// //
		// generalAnnotationLayer.addAnnotation(totalDeforestationAnnotation);
		// //
		// // ScreenAnnotation yearlyDeforestationAnnotation = new
		// ScreenAnnotation("Jährliche Abholzungsrate: "+
		// f.format(averageDefor*100) +"%", new Point(30, 120));
		// //
		// yearlyDeforestationAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// // yearlyDeforestationAnnotation.getAttributes().setCornerRadius(0);
		// //// legend.getAttributes().setSize(new Dimension(50, 0));
		// //
		// yearlyDeforestationAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// // yearlyDeforestationAnnotation.getAttributes().setDrawOffset(new
		// Point(100, 0)); // screen point is annotation bottom left corner
		// // // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // // legend.getAttributes().setImageOffset(new Point(1, 1));
		// // yearlyDeforestationAnnotation.getAttributes().setInsets(new
		// Insets(0, 40, 0, 0));
		// // yearlyDeforestationAnnotation.setAlwaysOnTop(true);
		// //
		// generalAnnotationLayer.addAnnotation(yearlyDeforestationAnnotation);
		// //
		// // ScreenAnnotation gdpAnnotation = new
		// ScreenAnnotation("Bruttoinlandsprodukt: "+ f.format(averageGDP)
		// +"R$", new Point(30, 85));
		// // gdpAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// // gdpAnnotation.getAttributes().setCornerRadius(0);
		// //// legend.getAttributes().setSize(new Dimension(50, 0));
		// //
		// gdpAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// // gdpAnnotation.getAttributes().setDrawOffset(new Point(100, 0)); //
		// screen point is annotation bottom left corner
		// // // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // // legend.getAttributes().setImageOffset(new Point(1, 1));
		// // gdpAnnotation.getAttributes().setInsets(new Insets(0, 40, 0, 0));
		// // gdpAnnotation.setAlwaysOnTop(true);
		// // generalAnnotationLayer.addAnnotation(gdpAnnotation);
		// //
		// //
		// // //ADDING STATEMENTS FOR YEARS
		// // if(year=="2006"){
		// // ScreenAnnotation statement2008Annotation = new
		// ScreenAnnotation("\"Brasiliens Fleisch Exporte steigen um 14% jährlich\"",
		// new Point(165, 380));
		// //
		// statement2008Annotation.getAttributes().setDefaults(defaultAttributes2);
		// // statement2008Annotation.getAttributes().setBackgroundColor(new
		// Color(0.2f, 0.2f, 0.2f, .5f));
		// // // backgroundAnnotation.getAttributes().setOpacity(0.5);
		// //
		// statement2008Annotation.getAttributes().setBorderColor(Color.BLACK);
		// // statement2008Annotation.getAttributes().setBorderWidth(1);
		// // // backgroundAnnotation.getAttributes().
		// // statement2008Annotation.getAttributes().setCornerRadius(0);
		// //
		// statement2008Annotation.getAttributes().setFont(Font.decode("Arial-BOLD-16"));
		// // statement2008Annotation.getAttributes().setSize(new Dimension(300,
		// 190));
		// // statement2008Annotation.setAlwaysOnTop(false);
		// //
		// statement2008Annotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIT_TEXT);
		// // use strict dimension width - 200
		// //
		// // generalAnnotationLayer.addAnnotation(statement2008Annotation);
		// // }
		// // if(year=="2007"){
		// // ScreenAnnotation statement2007Annotation = new
		// ScreenAnnotation("\"Brasilien ist der größte Fleischexporteur der Welt, mit 34% Anteil am globalen Markt.\"",
		// new Point(165, 380));
		// //
		// statement2007Annotation.getAttributes().setDefaults(defaultAttributes2);
		// // statement2007Annotation.getAttributes().setBackgroundColor(new
		// Color(0.2f, 0.2f, 0.2f, .5f));
		// // // backgroundAnnotation.getAttributes().setOpacity(0.5);
		// //
		// statement2007Annotation.getAttributes().setBorderColor(Color.BLACK);
		// // statement2007Annotation.getAttributes().setBorderWidth(1);
		// // // backgroundAnnotation.getAttributes().
		// // statement2007Annotation.getAttributes().setCornerRadius(0);
		// //
		// statement2007Annotation.getAttributes().setFont(Font.decode("Arial-BOLD-16"));
		// // statement2007Annotation.getAttributes().setSize(new Dimension(300,
		// 190));
		// // statement2007Annotation.setAlwaysOnTop(false);
		// // generalAnnotationLayer.addAnnotation(statement2007Annotation);
		// // }
		// // if(year=="2008"){
		// // ScreenAnnotation statement2008Annotation = new
		// ScreenAnnotation("\"37% der Beschäftigten Brasiliens arbeiten in der Landwirtschaft\"",
		// new Point(165, 380));
		// //
		// statement2008Annotation.getAttributes().setDefaults(defaultAttributes2);
		// // statement2008Annotation.getAttributes().setBackgroundColor(new
		// Color(0.2f, 0.2f, 0.2f, .5f));
		// // // backgroundAnnotation.getAttributes().setOpacity(0.5);
		// //
		// statement2008Annotation.getAttributes().setBorderColor(Color.BLACK);
		// // statement2008Annotation.getAttributes().setBorderWidth(1);
		// // // backgroundAnnotation.getAttributes().
		// // statement2008Annotation.getAttributes().setCornerRadius(0);
		// //
		// statement2008Annotation.getAttributes().setFont(Font.decode("Arial-BOLD-16"));
		// // statement2008Annotation.getAttributes().setSize(new Dimension(300,
		// 190));
		// // statement2008Annotation.setAlwaysOnTop(false);
		// // generalAnnotationLayer.addAnnotation(statement2008Annotation);
		// // }
		//
		// ScreenAnnotation statAnnotation = new ScreenAnnotation("", new
		// Point(175, 30));
		// statAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// statAnnotation.getAttributes().setBackgroundColor(new Color(0.2f,
		// 0.2f, 0.2f, .5f));
		// // backgroundAnnotation.getAttributes().setOpacity(0.5);
		// statAnnotation.getAttributes().setBorderColor(Color.BLACK);
		// statAnnotation.getAttributes().setBorderWidth(1);
		// // backgroundAnnotation.getAttributes().
		// statAnnotation.getAttributes().setCornerRadius(0);
		// statAnnotation.getAttributes().setFont(Font.decode("Arial-BOLD-17"));
		// // backgroundAnnotation.getAttributes().setSize(new Dimension(300,
		// 190));
		// statAnnotation.setAlwaysOnTop(false);
		// statAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIT_TEXT);
		// generalAnnotationLayer.addAnnotation(statAnnotation);
		//
		//
		// // ScreenAnnotation populationAnnotation = new
		// ScreenAnnotation("Bevölkerung: "+ population, new Point(30,190));
		// //
		// populationAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// // populationAnnotation.getAttributes().setCornerRadius(0);
		// //// legend.getAttributes().setSize(new Dimension(50, 0));
		// //
		// populationAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// // populationAnnotation.getAttributes().setDrawOffset(new Point(100,
		// 0)); // screen point is annotation bottom left corner
		// // // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // // legend.getAttributes().setImageOffset(new Point(1, 1));
		// // populationAnnotation.getAttributes().setInsets(new Insets(0, 40,
		// 0, 0));
		// // populationAnnotation.setAlwaysOnTop(true);
		// // generalAnnotationLayer.addAnnotation(populationAnnotation);
		// //
		// // ScreenAnnotation totalDeforestationAnnotation = new
		// ScreenAnnotation("Gesamte Abholzung: "+
		// f.format(averageAcumDefor*100) +"%", new Point(30, 155));
		// //
		// totalDeforestationAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// // totalDeforestationAnnotation.getAttributes().setCornerRadius(0);
		// //// legend.getAttributes().setSize(new Dimension(50, 0));
		// //
		// totalDeforestationAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// // totalDeforestationAnnotation.getAttributes().setDrawOffset(new
		// Point(100, 0)); // screen point is annotation bottom left corner
		// // // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // // legend.getAttributes().setImageOffset(new Point(1, 1));
		// // totalDeforestationAnnotation.getAttributes().setInsets(new
		// Insets(0, 40, 0, 0));
		// // totalDeforestationAnnotation.setAlwaysOnTop(true);
		// //
		// generalAnnotationLayer.addAnnotation(totalDeforestationAnnotation);
		// //
		// // ScreenAnnotation yearlyDeforestationAnnotation = new
		// ScreenAnnotation("Jährliche Abholzungsrate: "+
		// f.format(averageDefor*100) +"%", new Point(30, 120));
		// //
		// yearlyDeforestationAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// // yearlyDeforestationAnnotation.getAttributes().setCornerRadius(0);
		// //// legend.getAttributes().setSize(new Dimension(50, 0));
		// //
		// yearlyDeforestationAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// // yearlyDeforestationAnnotation.getAttributes().setDrawOffset(new
		// Point(100, 0)); // screen point is annotation bottom left corner
		// // // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // // legend.getAttributes().setImageOffset(new Point(1, 1));
		// // yearlyDeforestationAnnotation.getAttributes().setInsets(new
		// Insets(0, 40, 0, 0));
		// // yearlyDeforestationAnnotation.setAlwaysOnTop(true);
		// //
		// generalAnnotationLayer.addAnnotation(yearlyDeforestationAnnotation);
		// //
		// // ScreenAnnotation gdpAnnotation = new
		// ScreenAnnotation("Bruttoinlandsprodukt: "+ f.format(averageGDP)
		// +"R$", new Point(30, 85));
		// // gdpAnnotation.getAttributes().setDefaults(defaultAttributes2);
		// // gdpAnnotation.getAttributes().setCornerRadius(0);
		// //// legend.getAttributes().setSize(new Dimension(50, 0));
		// //
		// gdpAnnotation.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
		// // use strict dimension width - 200
		// // gdpAnnotation.getAttributes().setDrawOffset(new Point(100, 0)); //
		// screen point is annotation bottom left corner
		// // // legend.getAttributes().setHighlightScale(1); // No highlighting
		// either
		// // // legend.getAttributes().setImageOffset(new Point(1, 1));
		// // gdpAnnotation.getAttributes().setInsets(new Insets(0, 40, 0, 0));
		// // gdpAnnotation.setAlwaysOnTop(true);
		// // generalAnnotationLayer.addAnnotation(gdpAnnotation);
		//
		// if(year == "2002"){
		// factAnnotation.setText("2002");
		// statAnnotation.setText("Pará \n Bevölkerung: " +population +
		// "\n Gesamte Abholzung: "+f.format(averageAcumDefor*100)
		// +"%\n Bevölkerungsentwicklung: "+(population-populationLastYear));
		// }
		// if(year == "2003"){
		// factAnnotation.setText("2003");
		// statAnnotation.setText("Pará \n Bevölkerung: " +population +
		// "\n Gesamte Abholzung: "+f.format(averageAcumDefor*100)
		// +"%\n Bevölkerungsentwicklung: "+(population-populationLastYear));
		//
		// }
		// if(year == "2004"){
		// factAnnotation.setText("");
		// statAnnotation.setText("Pará \n Bevölkerung: " +population +
		// "\n Gesamte Abholzung: "+f.format(averageAcumDefor*100)
		// +"%\n Bevölkerungsentwicklung: "+(population-populationLastYear));
		//
		// }
		// if(year == "2005"){
		// factAnnotation.setText("");
		// statAnnotation.setText("Pará \n Bevölkerung: " +population +
		// "\n Gesamte Abholzung: "+f.format(averageAcumDefor*100)
		// +"%\n Bevölkerungsentwicklung: "+(population-populationLastYear));
		//
		// }
		// if(year == "2006"){
		// factAnnotation.setText("");
		// statAnnotation.setText("Pará \n Bevölkerung: " +population +
		// "\n Gesamte Abholzung: "+f.format(averageAcumDefor*100)
		// +"%\n Bevölkerungsentwicklung: "+(population-populationLastYear));
		//
		// }
		// if(year == "2007"){
		// factAnnotation.setText("");
		// statAnnotation.setText("Pará \n Bevölkerung: " +population +
		// "\n Gesamte Abholzung: "+f.format(averageAcumDefor*100)+"%\n Bevölkerungsentwicklung: "+(population-populationLastYear));
		//
		// }
		// if(year == "2008"){
		// factAnnotation.setText("");
		// statAnnotation.setText("Pará \n Bevölkerung: " +population +
		// "\n Gesamte Abholzung: "+f.format(averageAcumDefor*100)+"%\n Bevölkerungsentwicklung: "+(population-populationLastYear)
		// + "\nFläche: 1247689.5km²");
		//
		// }

		return generalAnnotationLayer;
	}

}
