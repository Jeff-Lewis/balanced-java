package customer_update;

import com.balancedpayments.*;
import com.balancedpayments.errors.*;
import java.util.HashMap;
import java.util.Map;

public class customer_update {

public static void main(String[] args) throws HTTPError, NoResultsFound, MultipleResultsFound {
Balanced.configure("ak-test-1xLFE6RLC1W3P4ePiQDI4UVpRwtKcdfqL");

Customer customer = new Customer("/customers/CU3BDNwUxRmQTDTQQatYGqr3");

Map<String, String> meta = new HashMap<String, String>();
meta.put("shipping-preference", "ground");

customer.meta = meta;
customer.email = "email@newdomain.com";

try {
    customer.save();
}
catch (HTTPError e) {}

}
}

