CREATE TABLE IF NOT EXISTS customer_action
(
    id          IDENTITY    NOT NULL,
    action_type VARCHAR(70) NOT NULL,
    customer_id UUID        NOT NULL,
    product_id  UUID,
    session_id  UUID,
    created     TIMESTAMP   NOT NULL,
    modified    TIMESTAMP,

    CONSTRAINT customer_action_id_primary_key PRIMARY KEY (id)
);

COMMENT ON COLUMN customer_action.action_type IS 'Type of action received';
COMMENT ON COLUMN customer_action.customer_id IS 'ID of customer who performed action';
COMMENT ON COLUMN customer_action.product_id IS 'Product related to action';
COMMENT ON COLUMN customer_action.session_id IS 'Session ID where action happened';
COMMENT ON COLUMN customer_action.created IS 'Date when action was logged';
COMMENT ON COLUMN customer_action.modified IS 'Date when action was modified';
