% if mode == 'definition':
Hold.capture(Map<String, Object> payload);

% else:
String apiKey = "";
String location = System.getProperty("balanced_location", Settings.location);
String key = System.getProperty("balanced_key", apiKey);
Settings.configure(location, key);

Map<String, Object> payload = new HashMap<String, Object>();
payload.put("hold_uri", "/v1/marketplaces/TEST-MP1cY43VkrOlypoTc5lxfstI/holds/HL3IKFgbk6IzYg4IhOjzzjTp");
payload.put("amount", 1000);
Hold hold = new Hold(payload);
hold.account_uri = "/v1/marketplaces/TEST-MP1cY43VkrOlypoTc5lxfstI/accounts/AC3z3msdgTHiowL349h94P22";
Account account = hold.getAccount();
Debit debit = hold.capture(1000);

% endif

