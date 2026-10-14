/**
 * Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated from the MedBiquitous Healthcare LOM v1 schemas.
 */
package edu.louisiana.caai.lom.model.address.impl;

import edu.louisiana.caai.lom.model.address.AddressCategoryType;
import edu.louisiana.caai.lom.model.address.AddressPackage;
import edu.louisiana.caai.lom.model.address.AddressType;
import edu.louisiana.caai.lom.model.address.CountryType;
import edu.louisiana.caai.lom.model.address.RestrictionsType;
import edu.louisiana.caai.lom.model.address.StreetAddressLineType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl#getID <em>ID</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl#getOrganization <em>Organization</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl#getStreetAddressLine <em>Street Address Line</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl#getCity <em>City</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl#getStateOrProvince <em>State Or Province</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl#getPostalCode <em>Postal Code</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl#getRegion <em>Region</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl#getDistrict <em>District</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl#getCountry <em>Country</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl#getAddressCategory <em>Address Category</em>}</li>
 *   <li>{@link edu.louisiana.caai.lom.model.address.impl.AddressTypeImpl#getRestrictions <em>Restrictions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AddressTypeImpl extends EObjectImpl implements AddressType {
	/**
	 * The default value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected String iD = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOrganization() <em>Organization</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganization()
	 * @generated
	 * @ordered
	 */
	protected EList<String> organization;

	/**
	 * The cached value of the '{@link #getStreetAddressLine() <em>Street Address Line</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStreetAddressLine()
	 * @generated
	 * @ordered
	 */
	protected EList<StreetAddressLineType> streetAddressLine;

	/**
	 * The default value of the '{@link #getCity() <em>City</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCity()
	 * @generated
	 * @ordered
	 */
	protected static final String CITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCity() <em>City</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCity()
	 * @generated
	 * @ordered
	 */
	protected String city = CITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getStateOrProvince() <em>State Or Province</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateOrProvince()
	 * @generated
	 * @ordered
	 */
	protected static final String STATE_OR_PROVINCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStateOrProvince() <em>State Or Province</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateOrProvince()
	 * @generated
	 * @ordered
	 */
	protected String stateOrProvince = STATE_OR_PROVINCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPostalCode() <em>Postal Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostalCode()
	 * @generated
	 * @ordered
	 */
	protected static final String POSTAL_CODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPostalCode() <em>Postal Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostalCode()
	 * @generated
	 * @ordered
	 */
	protected String postalCode = POSTAL_CODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRegion() <em>Region</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegion()
	 * @generated
	 * @ordered
	 */
	protected static final String REGION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRegion() <em>Region</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegion()
	 * @generated
	 * @ordered
	 */
	protected String region = REGION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDistrict() <em>District</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDistrict()
	 * @generated
	 * @ordered
	 */
	protected static final String DISTRICT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDistrict() <em>District</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDistrict()
	 * @generated
	 * @ordered
	 */
	protected String district = DISTRICT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCountry() <em>Country</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCountry()
	 * @generated
	 * @ordered
	 */
	protected CountryType country;

	/**
	 * The default value of the '{@link #getAddressCategory() <em>Address Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddressCategory()
	 * @generated
	 * @ordered
	 */
	protected static final AddressCategoryType ADDRESS_CATEGORY_EDEFAULT = AddressCategoryType.RESIDENTIAL;

	/**
	 * The cached value of the '{@link #getAddressCategory() <em>Address Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddressCategory()
	 * @generated
	 * @ordered
	 */
	protected AddressCategoryType addressCategory = ADDRESS_CATEGORY_EDEFAULT;

	/**
	 * This is true if the Address Category attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean addressCategoryESet;

	/**
	 * The default value of the '{@link #getRestrictions() <em>Restrictions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRestrictions()
	 * @generated
	 * @ordered
	 */
	protected static final RestrictionsType RESTRICTIONS_EDEFAULT = RestrictionsType.UNRESTRICTED;

	/**
	 * The cached value of the '{@link #getRestrictions() <em>Restrictions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRestrictions()
	 * @generated
	 * @ordered
	 */
	protected RestrictionsType restrictions = RESTRICTIONS_EDEFAULT;

	/**
	 * This is true if the Restrictions attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean restrictionsESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AddressTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AddressPackage.Literals.ADDRESS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getID() {
		return iD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setID(String newID) {
		String oldID = iD;
		iD = newID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADDRESS_TYPE__ID, oldID, iD));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getOrganization() {
		if (organization == null) {
			organization = new EDataTypeEList<String>(String.class, this, AddressPackage.ADDRESS_TYPE__ORGANIZATION);
		}
		return organization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<StreetAddressLineType> getStreetAddressLine() {
		if (streetAddressLine == null) {
			streetAddressLine = new EObjectContainmentEList<StreetAddressLineType>(StreetAddressLineType.class, this,
					AddressPackage.ADDRESS_TYPE__STREET_ADDRESS_LINE);
		}
		return streetAddressLine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCity() {
		return city;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCity(String newCity) {
		String oldCity = city;
		city = newCity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADDRESS_TYPE__CITY, oldCity, city));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStateOrProvince() {
		return stateOrProvince;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStateOrProvince(String newStateOrProvince) {
		String oldStateOrProvince = stateOrProvince;
		stateOrProvince = newStateOrProvince;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADDRESS_TYPE__STATE_OR_PROVINCE,
					oldStateOrProvince, stateOrProvince));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPostalCode(String newPostalCode) {
		String oldPostalCode = postalCode;
		postalCode = newPostalCode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADDRESS_TYPE__POSTAL_CODE,
					oldPostalCode, postalCode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRegion() {
		return region;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRegion(String newRegion) {
		String oldRegion = region;
		region = newRegion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADDRESS_TYPE__REGION, oldRegion,
					region));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDistrict() {
		return district;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDistrict(String newDistrict) {
		String oldDistrict = district;
		district = newDistrict;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADDRESS_TYPE__DISTRICT, oldDistrict,
					district));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CountryType getCountry() {
		return country;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCountry(CountryType newCountry, NotificationChain msgs) {
		CountryType oldCountry = country;
		country = newCountry;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AddressPackage.ADDRESS_TYPE__COUNTRY, oldCountry, newCountry);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCountry(CountryType newCountry) {
		if (newCountry != country) {
			NotificationChain msgs = null;
			if (country != null)
				msgs = ((InternalEObject) country).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - AddressPackage.ADDRESS_TYPE__COUNTRY, null, msgs);
			if (newCountry != null)
				msgs = ((InternalEObject) newCountry).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - AddressPackage.ADDRESS_TYPE__COUNTRY, null, msgs);
			msgs = basicSetCountry(newCountry, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADDRESS_TYPE__COUNTRY, newCountry,
					newCountry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AddressCategoryType getAddressCategory() {
		return addressCategory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAddressCategory(AddressCategoryType newAddressCategory) {
		AddressCategoryType oldAddressCategory = addressCategory;
		addressCategory = newAddressCategory == null ? ADDRESS_CATEGORY_EDEFAULT : newAddressCategory;
		boolean oldAddressCategoryESet = addressCategoryESet;
		addressCategoryESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADDRESS_TYPE__ADDRESS_CATEGORY,
					oldAddressCategory, addressCategory, !oldAddressCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetAddressCategory() {
		AddressCategoryType oldAddressCategory = addressCategory;
		boolean oldAddressCategoryESet = addressCategoryESet;
		addressCategory = ADDRESS_CATEGORY_EDEFAULT;
		addressCategoryESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, AddressPackage.ADDRESS_TYPE__ADDRESS_CATEGORY,
					oldAddressCategory, ADDRESS_CATEGORY_EDEFAULT, oldAddressCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetAddressCategory() {
		return addressCategoryESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RestrictionsType getRestrictions() {
		return restrictions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRestrictions(RestrictionsType newRestrictions) {
		RestrictionsType oldRestrictions = restrictions;
		restrictions = newRestrictions == null ? RESTRICTIONS_EDEFAULT : newRestrictions;
		boolean oldRestrictionsESet = restrictionsESet;
		restrictionsESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AddressPackage.ADDRESS_TYPE__RESTRICTIONS,
					oldRestrictions, restrictions, !oldRestrictionsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetRestrictions() {
		RestrictionsType oldRestrictions = restrictions;
		boolean oldRestrictionsESet = restrictionsESet;
		restrictions = RESTRICTIONS_EDEFAULT;
		restrictionsESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, AddressPackage.ADDRESS_TYPE__RESTRICTIONS,
					oldRestrictions, RESTRICTIONS_EDEFAULT, oldRestrictionsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetRestrictions() {
		return restrictionsESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case AddressPackage.ADDRESS_TYPE__STREET_ADDRESS_LINE:
			return ((InternalEList<?>) getStreetAddressLine()).basicRemove(otherEnd, msgs);
		case AddressPackage.ADDRESS_TYPE__COUNTRY:
			return basicSetCountry(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case AddressPackage.ADDRESS_TYPE__ID:
			return getID();
		case AddressPackage.ADDRESS_TYPE__ORGANIZATION:
			return getOrganization();
		case AddressPackage.ADDRESS_TYPE__STREET_ADDRESS_LINE:
			return getStreetAddressLine();
		case AddressPackage.ADDRESS_TYPE__CITY:
			return getCity();
		case AddressPackage.ADDRESS_TYPE__STATE_OR_PROVINCE:
			return getStateOrProvince();
		case AddressPackage.ADDRESS_TYPE__POSTAL_CODE:
			return getPostalCode();
		case AddressPackage.ADDRESS_TYPE__REGION:
			return getRegion();
		case AddressPackage.ADDRESS_TYPE__DISTRICT:
			return getDistrict();
		case AddressPackage.ADDRESS_TYPE__COUNTRY:
			return getCountry();
		case AddressPackage.ADDRESS_TYPE__ADDRESS_CATEGORY:
			return getAddressCategory();
		case AddressPackage.ADDRESS_TYPE__RESTRICTIONS:
			return getRestrictions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case AddressPackage.ADDRESS_TYPE__ID:
			setID((String) newValue);
			return;
		case AddressPackage.ADDRESS_TYPE__ORGANIZATION:
			getOrganization().clear();
			getOrganization().addAll((Collection<? extends String>) newValue);
			return;
		case AddressPackage.ADDRESS_TYPE__STREET_ADDRESS_LINE:
			getStreetAddressLine().clear();
			getStreetAddressLine().addAll((Collection<? extends StreetAddressLineType>) newValue);
			return;
		case AddressPackage.ADDRESS_TYPE__CITY:
			setCity((String) newValue);
			return;
		case AddressPackage.ADDRESS_TYPE__STATE_OR_PROVINCE:
			setStateOrProvince((String) newValue);
			return;
		case AddressPackage.ADDRESS_TYPE__POSTAL_CODE:
			setPostalCode((String) newValue);
			return;
		case AddressPackage.ADDRESS_TYPE__REGION:
			setRegion((String) newValue);
			return;
		case AddressPackage.ADDRESS_TYPE__DISTRICT:
			setDistrict((String) newValue);
			return;
		case AddressPackage.ADDRESS_TYPE__COUNTRY:
			setCountry((CountryType) newValue);
			return;
		case AddressPackage.ADDRESS_TYPE__ADDRESS_CATEGORY:
			setAddressCategory((AddressCategoryType) newValue);
			return;
		case AddressPackage.ADDRESS_TYPE__RESTRICTIONS:
			setRestrictions((RestrictionsType) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case AddressPackage.ADDRESS_TYPE__ID:
			setID(ID_EDEFAULT);
			return;
		case AddressPackage.ADDRESS_TYPE__ORGANIZATION:
			getOrganization().clear();
			return;
		case AddressPackage.ADDRESS_TYPE__STREET_ADDRESS_LINE:
			getStreetAddressLine().clear();
			return;
		case AddressPackage.ADDRESS_TYPE__CITY:
			setCity(CITY_EDEFAULT);
			return;
		case AddressPackage.ADDRESS_TYPE__STATE_OR_PROVINCE:
			setStateOrProvince(STATE_OR_PROVINCE_EDEFAULT);
			return;
		case AddressPackage.ADDRESS_TYPE__POSTAL_CODE:
			setPostalCode(POSTAL_CODE_EDEFAULT);
			return;
		case AddressPackage.ADDRESS_TYPE__REGION:
			setRegion(REGION_EDEFAULT);
			return;
		case AddressPackage.ADDRESS_TYPE__DISTRICT:
			setDistrict(DISTRICT_EDEFAULT);
			return;
		case AddressPackage.ADDRESS_TYPE__COUNTRY:
			setCountry((CountryType) null);
			return;
		case AddressPackage.ADDRESS_TYPE__ADDRESS_CATEGORY:
			unsetAddressCategory();
			return;
		case AddressPackage.ADDRESS_TYPE__RESTRICTIONS:
			unsetRestrictions();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case AddressPackage.ADDRESS_TYPE__ID:
			return ID_EDEFAULT == null ? iD != null : !ID_EDEFAULT.equals(iD);
		case AddressPackage.ADDRESS_TYPE__ORGANIZATION:
			return organization != null && !organization.isEmpty();
		case AddressPackage.ADDRESS_TYPE__STREET_ADDRESS_LINE:
			return streetAddressLine != null && !streetAddressLine.isEmpty();
		case AddressPackage.ADDRESS_TYPE__CITY:
			return CITY_EDEFAULT == null ? city != null : !CITY_EDEFAULT.equals(city);
		case AddressPackage.ADDRESS_TYPE__STATE_OR_PROVINCE:
			return STATE_OR_PROVINCE_EDEFAULT == null ? stateOrProvince != null
					: !STATE_OR_PROVINCE_EDEFAULT.equals(stateOrProvince);
		case AddressPackage.ADDRESS_TYPE__POSTAL_CODE:
			return POSTAL_CODE_EDEFAULT == null ? postalCode != null : !POSTAL_CODE_EDEFAULT.equals(postalCode);
		case AddressPackage.ADDRESS_TYPE__REGION:
			return REGION_EDEFAULT == null ? region != null : !REGION_EDEFAULT.equals(region);
		case AddressPackage.ADDRESS_TYPE__DISTRICT:
			return DISTRICT_EDEFAULT == null ? district != null : !DISTRICT_EDEFAULT.equals(district);
		case AddressPackage.ADDRESS_TYPE__COUNTRY:
			return country != null;
		case AddressPackage.ADDRESS_TYPE__ADDRESS_CATEGORY:
			return isSetAddressCategory();
		case AddressPackage.ADDRESS_TYPE__RESTRICTIONS:
			return isSetRestrictions();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (iD: ");
		result.append(iD);
		result.append(", organization: ");
		result.append(organization);
		result.append(", city: ");
		result.append(city);
		result.append(", stateOrProvince: ");
		result.append(stateOrProvince);
		result.append(", postalCode: ");
		result.append(postalCode);
		result.append(", region: ");
		result.append(region);
		result.append(", district: ");
		result.append(district);
		result.append(", addressCategory: ");
		if (addressCategoryESet)
			result.append(addressCategory);
		else
			result.append("<unset>");
		result.append(", restrictions: ");
		if (restrictionsESet)
			result.append(restrictions);
		else
			result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //AddressTypeImpl
