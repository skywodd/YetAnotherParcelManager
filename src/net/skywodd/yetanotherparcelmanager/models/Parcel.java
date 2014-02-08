/*
 *  This file is part of YetAnotherParcelManager.
 *
 *   YetAnotherParcelManager is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   YetAnotherParcelManager is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with YetAnotherParcelManager. If not, see <http://www.gnu.org/licenses/>.
 */
package net.skywodd.yetanotherparcelmanager.models;

import android.net.Uri;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * This class is an ORMLite-able container for parcel's informations. Multiple
 * parcels can have the same name but will be identified by their ID. Parcels
 * are stored in the "parcels" table.
 * 
 * @author skywodd
 * @version 1.0.0
 */
@DatabaseTable(tableName = "parcels")
public class Parcel {

	/** Parcel ID */
	@DatabaseField(generatedId = true)
	private long id;

	/** Parcel name */
	@DatabaseField(id = true)
	private String name;

	/** Parcel actual culture */
	@DatabaseField
	private Growing growing;

	/** Parcel previous culture */
	@DatabaseField
	private Growing lastGrowing;

	/** Parcel surface */
	@DatabaseField
	private int surface;

	/** Parcel image */
	@DatabaseField
	private Uri image;

	/** Parcel GPS latitude */
	@DatabaseField
	private double latitude;

	/** Parcel GPS longitude */
	@DatabaseField
	private double longitude;

	/** Parcel address */
	@DatabaseField
	private String address;

	/**
	 * Default constructor for ORMLite ONLY.
	 */
	public Parcel() {
		// ORMLite needs a no-arg constructor
	}

	/**
	 * Complete constructor of the parcel class.
	 * 
	 * @param name
	 *            Parcel name.
	 * @param growing
	 *            Actual growing culture.
	 * @param lastGrowing
	 *            Previous culture.
	 * @param surface
	 *            Parcel surface.
	 * @param image
	 *            Parcel image URI.
	 * @param latitude
	 *            Parcel GPS latitude.
	 * @param longitude
	 *            Parcel GPS longitude.
	 * @param address
	 *            Parcel address.
	 */
	public Parcel(String name, Growing growing, Growing lastGrowing,
			int surface, Uri image, double latitude, double longitude,
			String address) {
		this.id = -1;
		this.name = name;
		this.growing = growing;
		this.lastGrowing = lastGrowing;
		this.surface = surface;
		this.image = image;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
	}

	/**
	 * Return the parcel ID.
	 * 
	 * @return The parcel ID.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Return the parcel name.
	 * 
	 * @return The parcel name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the parcel name.
	 * 
	 * @param name
	 *            The new parcel name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the currently growing culture type.
	 * 
	 * @return The currently growing culture type.
	 */
	public Growing getGrowing() {
		return growing;
	}

	/**
	 * Set the currently growing culture type.
	 * 
	 * @param growing
	 *            The new currently growing culture type.
	 */
	public void setGrowing(Growing growing) {
		this.growing = growing;
	}

	/**
	 * Get the previous growing culture type.
	 * 
	 * @return The previous growing culture type.
	 */
	public Growing getLastGrowing() {
		return lastGrowing;
	}

	/**
	 * Get the previous growing culture type.
	 * 
	 * @param lastGrowing
	 *            The new previous growing culture type.
	 */
	public void setLastGrowing(Growing lastGrowing) {
		this.lastGrowing = lastGrowing;
	}

	/**
	 * Set the currently growing culture type with backup of the current value
	 * in the previous culture variable.
	 * 
	 * @param growing
	 *            The new currently growing culture type.
	 */
	public void changeGrowing(Growing growing) {
		this.lastGrowing = this.growing;
		this.growing = growing;
	}

	/**
	 * Get the parcel surface.
	 * 
	 * @return The parcel surface.
	 */
	public int getSurface() {
		return surface;
	}

	/**
	 * Set the parcel surface.
	 * 
	 * @param surface
	 *            The new parcel surface.
	 */
	public void setSurface(int surface) {
		this.surface = surface;
	}

	/**
	 * Get the parcel image URI.
	 * 
	 * @return The parcel image URI.
	 */
	public Uri getImage() {
		return image;
	}

	/**
	 * Set the parcel image URI.
	 * 
	 * @param image
	 *            The new parcel image URI.
	 */
	public void setImage(Uri image) {
		this.image = image;
	}

	/**
	 * Get the parcel GPS latitude.
	 * 
	 * @return The parcel GPS latitude.
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Set the parcel GPS latitude.
	 * 
	 * @param latitude
	 *            The new parcel GPS latitude.
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Get the parcel GPS longitude.
	 * 
	 * @return The parcel GPS longitude.
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Set the parcel GPS longitude.
	 * 
	 * @param longitude
	 *            The new parcel GPS longitude.
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Get the parcel address.
	 * 
	 * @return The parcel address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the parcel address.
	 * 
	 * @param address
	 *            The new parcel address.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Parcel [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", growing=");
		builder.append(growing);
		builder.append(", lastGrowing=");
		builder.append(lastGrowing);
		builder.append(", surface=");
		builder.append(surface);
		builder.append(", image=");
		builder.append(image);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", address=");
		builder.append(address);
		builder.append("]");
		return builder.toString();
	}
}
