% if mode == 'definition':
Callback().delete()

% else:
Balanced.configure("ak-test-2eKlj1ZDfAcZSARMf3NMhBHywDej0avSY");

Callback callback = new Callback("/callbacks/CB4a7Q7HSdJJgMVHwPsarIw8");

try {
    callback.delete();
}
catch (APIError e) {}
catch (NotCreated e) {}

% endif

