% if mode == 'definition':
Account.credits;

% else:
Settings.configure("2776ea40d92e11e29fe1026ba7cac9da");

Account account = new Account("/v1/bank_accounts/BA1iViFZ5fKWIixl3fpq07Je");
Credit.Collection credits = account.credits;

% endif

