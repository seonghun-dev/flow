DROP TABLE IF EXISTS extension;

CREATE TABLE extension (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           is_on BOOLEAN NOT NULL DEFAULT TRUE,
                           is_custom BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO extension (name, is_on, is_custom) VALUES
                                                          ('bat', FALSE, FALSE),
                                                          ('cmd', FALSE, FALSE),
                                                          ('com', FALSE, FALSE),
                                                          ('cpl', FALSE, FALSE),
                                                          ('exe', FALSE, FALSE),
                                                          ('scr', FALSE, FALSE),
                                                          ('js', FALSE, FALSE);
