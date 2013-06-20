% if mode == 'definition':
Card.Collection cards = new Card.Collection(String uri);

% else:
String apiKey = "";
String location = System.getProperty("balanced_location", Settings.location);
String key = System.getProperty("balanced_key", apiKey);
Settings.configure(location, key);

Card.Collection cards = new Card.Collection("/v1/marketplaces/TEST-MP1cY43VkrOlypoTc5lxfstI/cards");

% endif

