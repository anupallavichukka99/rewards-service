INSERT INTO customer(customer_id, customer_name)
VALUES
(101, 'Anu'),
(102, 'Dhanvika'),
(103, 'Siva'),
(104, 'Pallavi');

INSERT INTO customer_transactions
(transaction_id, amount, transaction_date, customer_id)
VALUES
(1, 120.00, '2026-05-10', 101),
(2, 110.00, '2026-03-15', 101),
(6, 130.00, '2026-04-03', 101),
(3, 200.00, '2026-05-20', 102),
(4, 290.00, '2026-05-25', 103),
(7, 290.00, '2026-02-25', 102),
(5, 180.00, '2026-04-10', 102);