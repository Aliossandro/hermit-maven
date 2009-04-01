package org.semanticweb.HermiT.datatypes.anyuri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import org.semanticweb.HermiT.Prefixes;
import org.semanticweb.HermiT.model.DatatypeRestriction;
import org.semanticweb.HermiT.datatypes.DatatypeHandler;
import org.semanticweb.HermiT.datatypes.MalformedLiteralException;
import org.semanticweb.HermiT.datatypes.UnsupportedFacetException;
import org.semanticweb.HermiT.datatypes.ValueSpaceSubset;
import org.semanticweb.HermiT.datatypes.common.EmptyValueSpaceSubset;

public class AnyURIDatatypeHandler implements DatatypeHandler {
    protected static final String XSD_ANY_URI=Prefixes.s_semanticWebPrefixes.get("xsd")+"anyURI";
    protected static final ValueSpaceSubset ANY_URI_ALL=new AnyURIAll();
    protected static final ValueSpaceSubset EMPTY=new EmptyValueSpaceSubset(XSD_ANY_URI);
    
    protected final Set<String> m_managedDatatypeURIs;
    protected final Set<Class<?>> m_managedDataValueClasses;

    public AnyURIDatatypeHandler() {
        m_managedDatatypeURIs=Collections.singleton(XSD_ANY_URI);
        m_managedDataValueClasses=new HashSet<Class<?>>();
        m_managedDataValueClasses.add(URI.class);
    }
    public Set<String> getManagedDatatypeURIs() {
        return m_managedDatatypeURIs;
    }
    public Set<Class<?>> getManagedDataValueClasses() {
        return m_managedDataValueClasses;
    }
    public String toString(Prefixes prefixes,Object dataValue) {
        String lexicalForm=((URI)dataValue).toString();
        return '\"'+lexicalForm+"\"^^"+prefixes.abbreviateURI(XSD_ANY_URI);
    }
    public Object parseLiteral(String lexicalForm,String datatypeURI) throws MalformedLiteralException {
        assert XSD_ANY_URI.equals(datatypeURI);
        try {
            return new URI(lexicalForm);
        }
        catch (URISyntaxException error) {
            throw new MalformedLiteralException(lexicalForm,datatypeURI);
        }
    }
    public void validateDatatypeRestriction(DatatypeRestriction datatypeRestriction) throws UnsupportedFacetException {
        assert XSD_ANY_URI.equals(datatypeRestriction.getDatatypeURI());
        if (datatypeRestriction.getNumberOfFacetRestrictions()>0)
            throw new UnsupportedFacetException("xsd:anyURI does not provide any facets.");
    }
    public ValueSpaceSubset createValueSpaceSubset(DatatypeRestriction datatypeRestriction) {
        assert XSD_ANY_URI.equals(datatypeRestriction.getDatatypeURI());
        assert datatypeRestriction.getNumberOfFacetRestrictions()==0;
        return ANY_URI_ALL;
    }
    public ValueSpaceSubset conjoinWithDR(ValueSpaceSubset valueSpaceSubset,DatatypeRestriction datatypeRestriction) {
        assert XSD_ANY_URI.equals(datatypeRestriction.getDatatypeURI());
        assert datatypeRestriction.getNumberOfFacetRestrictions()==0;
        return ANY_URI_ALL;
    }
    public ValueSpaceSubset conjoinWithDRNegation(ValueSpaceSubset valueSpaceSubset,DatatypeRestriction datatypeRestriction) {
        assert XSD_ANY_URI.equals(datatypeRestriction.getDatatypeURI());
        assert datatypeRestriction.getNumberOfFacetRestrictions()==0;
        return EMPTY;
    }
    public boolean isSubsetOf(String subsetDatatypeURI,String supersetDatatypeURI) {
        assert XSD_ANY_URI.equals(subsetDatatypeURI);
        assert XSD_ANY_URI.equals(supersetDatatypeURI);
        return true;
    }
    public boolean isDisjointWith(String datatypeURI1,String datatypeURI2) {
        assert XSD_ANY_URI.equals(datatypeURI1);
        assert XSD_ANY_URI.equals(datatypeURI2);
        return false;
    }

    protected static class AnyURIAll implements ValueSpaceSubset {

        public String getDatatypeURI() {
            return XSD_ANY_URI;
        }
        public boolean hasCardinalityAtLeast(int number) {
            return true;
        }
        public boolean containsDataValue(Object dataValue) {
            assert dataValue instanceof URI;
            return true;
        }
        public void enumerateDataValues(Collection<Object> dataValues) {
            throw new UnsupportedOperationException("The value space is infinite.");
        }
    }
}