package reversal_update;

import com.balancedpayments.*;
import com.balancedpayments.errors.*;
import java.util.HashMap;
import java.util.Map;

public class reversal_update {

public static void main(String[] args) throws HTTPError, NoResultsFound, MultipleResultsFound {
Balanced.configure("ak-test-2eKlj1ZDfAcZSARMf3NMhBHywDej0avSY");

Reversal reversal = new Reversal("/reversals/RV6AleFrrhNHBDpr9W9ozGmY");

Map<String, String> meta = new HashMap<String, String>();
meta.put("reversal.reason", "user not happy with product");
meta.put("user.notes", "very polite on the phone");
meta.put("user.satisfaction", "6");

reversal.meta = meta;
reversal.description = "update this description";

try {
    reversal.save();
}
catch (HTTPError e) {}


}
}

