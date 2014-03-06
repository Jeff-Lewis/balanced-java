package card_hold_create;

import com.balancedpayments.*;
import com.balancedpayments.errors.*;
import java.util.HashMap;
import java.util.Map;

public class card_hold_create {

public static void main(String[] args) throws HTTPError, NoResultsFound, MultipleResultsFound {
Balanced.configure("ak-test-2cSDy37BKy5K4NUHKHVNXNTjTHPEqjRtB");

Card card = new Card("/cards/CC3ZsWHP2jMgvFrrzDzfZS0q");

Map<String, Object> payload = new HashMap<String, Object>();
payload.put("amount", 5000);
payload.put("description", "Some descriptive text for the debit in the dashboard");

try {
    CardHold cardHold = card.hold(payload);
}
catch (HTTPError e) {}

}
}

