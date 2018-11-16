package com.empl.mgr.service;

import com.empl.mgr.support.JSONReturn;

public abstract interface AddressService {

	/**
	 * @author Ryan
	 * @return
	 */
	public abstract JSONReturn findProvinceAll();

	/**
	 * @author Ryan
	 * @param provinceId
	 * @return
	 */
	public abstract JSONReturn findCityByProvinceId(long provinceId);

	/**
	 * @author Ryan
	 * @param cityId
	 * @return
	 */
	public abstract JSONReturn findCountyByCityId(long cityId);

	/**
	 * @author Ryan
	 * @param countyId
	 * @return
	 */
	public abstract JSONReturn findTownshipByCountyId(long countyId);

	/**
	 * @author Ryan
	 * @param towhshipId
	 * @return
	 */
	public abstract JSONReturn findVillageByTownshipId(long towhshipId);

}
