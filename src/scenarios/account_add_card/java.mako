% if mode == 'definition':
Account.associateCard(String uri);

% else:
String apiKey = "";
String location = System.getProperty("balanced_location", Settings.location);
String key = System.getProperty("balanced_key", apiKey);
Settings.configure(location, key);

Account account = new Account("/v1/marketplaces/TEST-MP1cY43VkrOlypoTc5lxfstI/accounts/AC1DOd9xMVGdZSGUeyrducwU");
account.associateCard("/v1/marketplaces/TEST-MP1cY43VkrOlypoTc5lxfstI/cards/CC1FuQR6sXB07wmrWwwFF2Wj");

% endif

