CREATE TABLE booking (
  id SERIAL PRIMARY KEY,
  phone_id INT NOT NULL,
  user_id INT NOT NULL,
  booked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  returned_at TIMESTAMP,

  FOREIGN KEY (phone_id) REFERENCES phones(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE INDEX user_idx ON booking (user_id);
