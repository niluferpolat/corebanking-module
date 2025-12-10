--added initial user and its account for testing
INSERT INTO users(id, username, email, password, created_at)
VALUES (
    gen_random_uuid(),
    'test_rockerfeller',
    'rockerfeller@hotmail.com',
    '$2a$10$JD9yHyfWAwNco8ZJxx6oMu2FMQWGj8shhnJreYszKpn3IqJTnlv3O',
    NOW()
);
INSERT INTO account(id, number, name, balance, user_id, created_at)
VALUES (
    gen_random_uuid(),
    '1234567890',
    'Test Hesabı',
    1000,
    (SELECT id FROM users WHERE email = 'rockerfeller@hotmail.com'),
    NOW()
);
