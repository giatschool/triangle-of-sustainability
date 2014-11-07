package de.ifgi.worldwind.layer;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.font.TextAttribute;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import gov.nasa.worldwind.*;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.render.UserFacingIcon;

/**
 * @author Patrick Murris - patmurris.blogspot.com
 * @version 0.1 - 20070525 - Very first shot at a WW XML icon layer loader Reads
 *          a XML WW icon layer definition and create the corresponding
 *          Iconlayer
 *          ------------------------------------------------------------
 *          ---------------- Issues : doesnt support layer hierarchy (path) to a
 *          particular layer.
 *          ----------------------------------------------------
 *          ------------------------
 */

public class WWXMLIconLayer extends IconLayer implements SelectListener {

	public WWXMLIconLayer(String fileName, String layerName) {
		this.setName(layerName);
		this.loadIcons(readXML(new File(fileName)), layerName);
	}

	// Load icons from XML document named layer
	private void loadIcons(Document doc, String layerName) {
		// Get LayerSet node
		Node node = findLayerSetByName(doc, layerName);
		if (node == null)
			return; // TODO: throw exception

		loadLayerSet(node);
	}

	// Load icons from a LayerSet node (recursive)
	private void loadLayerSet(Node node) {
		// Process icon child nodes
		Node child;
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			child = node.getChildNodes().item(i);
			if (child.getNodeName().compareToIgnoreCase("ChildLayerSet") == 0) {
				loadLayerSet(child); // recurse
			} else if (child.getNodeName().compareToIgnoreCase("Icon") == 0) {
				// Get icon info
				Node n;
				n = findChildByName(child, "Name");
				String name = n.getTextContent();
				n = findChildByName(child, "Latitude");
				double latitude = Double
						.parseDouble(findChildByName(n, "Value")
								.getTextContent());
				n = findChildByName(child, "Longitude");
				double longitude = Double.parseDouble(findChildByName(n,
						"Value").getTextContent());
				n = findChildByName(child, "DistanceAboveSurface");
				double distanceAboveSurface = Double.parseDouble(n
						.getTextContent());
				n = findChildByName(child, "TextureFilePath");
				String textureFilePath = n.getTextContent();
				n = findChildByName(child, "TextureWidthPixels");
				int textureWidthPixels = Integer.parseInt(n.getTextContent());
				n = findChildByName(child, "TextureHeightPixels");
				int textureHeightPixels = Integer.parseInt(n.getTextContent());
				n = findChildByName(child, "IconWidthPixels");
				int iconWidthPixels = Integer.parseInt(n.getTextContent());
				n = findChildByName(child, "IconHeightPixels");
				int iconHeightPixels = Integer.parseInt(n.getTextContent());
				String clickableUrl = "";
				n = findChildByName(child, "ClickableUrl");
				if (n != null)
					clickableUrl = n.getTextContent();
				String description = "";
				n = findChildByName(child, "Description");
				if (n != null)
					description = n.getTextContent();

				// Add icon
				ClickableIcon icon = new ClickableIcon(textureFilePath,
						new Position(Angle.fromDegrees(latitude),
								Angle.fromDegrees(longitude),
								distanceAboveSurface));
				icon.setHighlightScale(1.5);
				icon.setToolTipFont(this.makeToolTipFont());
				icon.setToolTipText(name);
				// if(description.length() > 0) icon.setToolTipText(name + " : "
				// + description);
				icon.setToolTipTextColor(java.awt.Color.DARK_GRAY);
				icon.setSize(new Dimension(iconWidthPixels, iconHeightPixels));
				icon.setName(name);
				icon.setDescription(description);
				icon.setUrl(clickableUrl);
				icon.setParent(this);
				this.addIcon(icon);
			}
		}
	}

	// Select listener
	private ClickableIcon lastToolTipIcon = null;

	public void selected(SelectEvent event) {
		if (event.hasObjects() && event.getTopObject() instanceof ClickableIcon
				&& ((ClickableIcon) event.getTopObject()).getParent() != this)
			return;

		if (event.getEventAction().equals(SelectEvent.LEFT_CLICK)) {
			if (event.hasObjects()) {
				if (event.getTopObject() instanceof ClickableIcon) {
					ClickableIcon icon = (ClickableIcon) event.getTopObject();
					if (icon.getUrl().startsWith("http")) {
						// Launch local web browser
						try {
							// Desktop.getDesktop().browse(new
							// URI(lastToolTipIcon.getUrl()));
							Runtime.getRuntime().exec(
									"cmd /c start " + icon.getUrl());
						} catch (Exception e) {
						}
					}
				}
			}
		} else if (event.getEventAction().equals(SelectEvent.HOVER)) {
			if (lastToolTipIcon != null) {
				lastToolTipIcon.setShowToolTip(false);
				this.lastToolTipIcon = null;
				// this.wwd.repaint();
			}

			if (event.hasObjects()) {
				if (event.getTopObject() instanceof ClickableIcon) {
					this.lastToolTipIcon = (ClickableIcon) event.getTopObject();
					lastToolTipIcon.setShowToolTip(true);
					// this.wwd.repaint();
				}
			}
		} else if (event.getEventAction().equals(SelectEvent.ROLLOVER)) {
			this.highlight(event.getTopObject());
			// this.wwd.repaint();
		}
	}

	private ClickableIcon lastPickedIcon;

	private void highlight(Object o) {
		if (this.lastPickedIcon == o)
			return; // same thing selected

		if (this.lastPickedIcon != null) {
			this.lastPickedIcon.setHighlighted(false);
			this.lastPickedIcon = null;
		}

		if (o != null && o instanceof ClickableIcon) {
			this.lastPickedIcon = (ClickableIcon) o;
			this.lastPickedIcon.setHighlighted(true);
		}
	}

	// Read an XML document from a file
	private static Document readXML(File file) {
		Document doc = null;
		if (file == null) {
			// String message =
			// WorldWind.retrieveErrMsg("nullValue.FileIsNull");
			// WorldWind.logger().log(java.util.logging.Level.FINE, message);
			// throw new IllegalArgumentException(message);
		}

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			docBuilderFactory.setNamespaceAware(false);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(file);

			// TODO: use proper error message strings - not GeoRSS
		} catch (ParserConfigurationException e) {
			// String message =
			// WorldWind.retrieveErrMsg("GeoRSS.ParserConfigurationException");
			// WorldWind.logger().log(java.util.logging.Level.FINE, message, e);
			// throw new WWRuntimeException(message, e);
		} catch (IOException e) {
			// String message =
			// WorldWind.retrieveErrMsg("GeoRSS.IOExceptionParsing") +
			// file.getPath();
			// WorldWind.logger().log(java.util.logging.Level.FINE, message, e);
			// throw new WWRuntimeException(message, e);
		} catch (SAXException e) {
			// String message =
			// WorldWind.retrieveErrMsg("GeoRSS.IOExceptionParsing") +
			// file.getPath();
			// WorldWind.logger().log(java.util.logging.Level.FINE, message, e);
			// throw new WWRuntimeException(message, e);
		}
		return doc;
	}

	// Find a LayerSet node by its name - exact match case sens.
	private static Node findLayerSetByName(Document doc, String layerName) {
		NodeList nodes = doc.getElementsByTagName("LayerSet");
		if (nodes.getLength() < 1)
			return null;
		// Find layerset node for layerName
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			Node name = node.getAttributes().getNamedItem("Name");
			if (name != null && name.getTextContent().equals(layerName))
				return node;
		}
		return null;
	}

	// Find a child node by its name - not case sensitive
	private static Node findChildByName(Node parent, String name) {
		NodeList children = parent.getChildNodes();
		if (children == null || children.getLength() < 1)
			return null;

		for (int i = 0; i < children.getLength(); i++) {
			String n = children.item(i).getNodeName();
			if (n != null && n.toLowerCase().equals(name.toLowerCase()))
				return children.item(i);
		}

		return null;
	}

	// TODO: make font and color configurable
	private Font makeToolTipFont() {
		HashMap<TextAttribute, Object> fontAttributes = new HashMap<TextAttribute, Object>();
		fontAttributes.put(TextAttribute.BACKGROUND, new java.awt.Color(0.4f,
				0.4f, 0.4f, 1f));
		return Font.decode("Arial-BOLD-14").deriveFont(fontAttributes);
	}

	// Extended UserFacingIcon class to hold extra information
	// like name, description and url
	public class ClickableIcon extends UserFacingIcon {
		private IconLayer parent;
		private String description;
		private String name;
		private String url;

		public ClickableIcon(String iconPath, Position iconPosition) {
			super(iconPath, iconPosition);
		}

		public IconLayer getParent() {
			return parent;
		}

		public void setParent(IconLayer layer) {
			this.parent = layer;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	} // End ClickableIcon class

} // End class
