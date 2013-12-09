package pl.mbos.bachelor_thesis.controller;

/**
 * Interface implemented by objects that can pass information about change in webservice address.
 */
public interface WebAddressListener {

    public void webServiceAddressChanged(String newAddress);
}
