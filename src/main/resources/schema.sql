DROP TABLE IF EXISTS extension;

CREATE TABLE extension (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           is_on BOOLEAN NOT NULL DEFAULT TRUE,
                           is_custom BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO extension (name, is_on, is_custom) VALUES
                                                          ('bat', TRUE, FALSE),
                                                          ('cmd', TRUE, FALSE),
                                                          ('com', TRUE, FALSE),
                                                          ('cpl', TRUE, FALSE),
                                                          ('exe', TRUE, FALSE),
                                                          ('scr', TRUE, FALSE),
                                                          ('js', TRUE, FALSE);
