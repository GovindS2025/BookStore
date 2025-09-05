-- Migration: Create Saga Tables
-- Version: 2
-- Description: Create tables for Saga Pattern implementation

-- Create saga_status table
CREATE TABLE IF NOT EXISTS saga_status (
    id BIGSERIAL PRIMARY KEY,
    order_id VARCHAR(255) NOT NULL UNIQUE,
    saga_type VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,
    started_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    failed_at TIMESTAMP,
    failure_reason TEXT,
    retry_count INTEGER DEFAULT 0,
    compensation_data TEXT,
    metadata JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create saga_events table
CREATE TABLE IF NOT EXISTS saga_events (
    id BIGSERIAL PRIMARY KEY,
    order_id VARCHAR(255) NOT NULL,
    step_name VARCHAR(100) NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    payload TEXT,
    error_message TEXT,
    executed_at TIMESTAMP,
    compensated_at TIMESTAMP,
    retry_count INTEGER DEFAULT 0,
    metadata JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_saga_status_order_id ON saga_status(order_id);
CREATE INDEX IF NOT EXISTS idx_saga_status_state ON saga_status(state);
CREATE INDEX IF NOT EXISTS idx_saga_status_saga_type ON saga_status(saga_type);
CREATE INDEX IF NOT EXISTS idx_saga_status_retry_count ON saga_status(retry_count);
CREATE INDEX IF NOT EXISTS idx_saga_status_started_at ON saga_status(started_at);

CREATE INDEX IF NOT EXISTS idx_saga_events_order_id ON saga_events(order_id);
CREATE INDEX IF NOT EXISTS idx_saga_events_step_name ON saga_events(step_name);
CREATE INDEX IF NOT EXISTS idx_saga_events_status ON saga_events(status);
CREATE INDEX IF NOT EXISTS idx_saga_events_created_at ON saga_events(created_at);

-- Create composite indexes for common query patterns
CREATE INDEX IF NOT EXISTS idx_saga_events_order_step ON saga_events(order_id, step_name);
CREATE INDEX IF NOT EXISTS idx_saga_events_order_status ON saga_events(order_id, status);

-- Add comments for documentation
COMMENT ON TABLE saga_status IS 'Tracks the overall state of saga workflows for orders';
COMMENT ON TABLE saga_events IS 'Tracks individual saga step executions and their states';

COMMENT ON COLUMN saga_status.order_id IS 'Unique identifier for the order associated with this saga';
COMMENT ON COLUMN saga_status.saga_type IS 'Type of saga workflow (e.g., ORDER_PROCESSING)';
COMMENT ON COLUMN saga_status.state IS 'Current state of the saga workflow';
COMMENT ON COLUMN saga_status.retry_count IS 'Number of retry attempts for failed steps';
COMMENT ON COLUMN saga_status.compensation_data IS 'Data needed for compensation operations';
COMMENT ON COLUMN saga_status.metadata IS 'Additional metadata for the saga';

COMMENT ON COLUMN saga_events.order_id IS 'Order ID associated with this saga event';
COMMENT ON COLUMN saga_events.step_name IS 'Name of the saga step being executed';
COMMENT ON COLUMN saga_events.event_type IS 'Type of event (e.g., SAGA_STARTED, STEP_COMPLETED)';
COMMENT ON COLUMN saga_events.status IS 'Current status of the saga step';
COMMENT ON COLUMN saga_events.payload IS 'Data payload for the saga step';
COMMENT ON COLUMN saga_events.error_message IS 'Error message if the step failed';
COMMENT ON COLUMN saga_events.retry_count IS 'Number of retry attempts for this step';
COMMENT ON COLUMN saga_events.metadata IS 'Additional metadata for the saga step';

-- Create a view for easy saga monitoring
CREATE OR REPLACE VIEW saga_monitoring AS
SELECT 
    ss.order_id,
    ss.saga_type,
    ss.state as saga_state,
    ss.started_at,
    ss.completed_at,
    ss.failed_at,
    ss.retry_count,
    ss.failure_reason,
    COUNT(se.id) as total_events,
    COUNT(CASE WHEN se.status = 'COMPLETED' THEN 1 END) as completed_events,
    COUNT(CASE WHEN se.status = 'FAILED' THEN 1 END) as failed_events,
    COUNT(CASE WHEN se.status = 'PENDING' THEN 1 END) as pending_events,
    EXTRACT(EPOCH FROM (COALESCE(ss.completed_at, CURRENT_TIMESTAMP) - ss.started_at)) as duration_seconds
FROM saga_status ss
LEFT JOIN saga_events se ON ss.order_id = se.order_id
GROUP BY ss.id, ss.order_id, ss.saga_type, ss.state, ss.started_at, ss.completed_at, ss.failed_at, ss.retry_count, ss.failure_reason;

-- Create a function to get saga statistics
CREATE OR REPLACE FUNCTION get_saga_statistics()
RETURNS TABLE(
    total_sagas BIGINT,
    completed_sagas BIGINT,
    failed_sagas BIGINT,
    compensating_sagas BIGINT,
    active_sagas BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        COUNT(*) as total_sagas,
        COUNT(CASE WHEN state = 'COMPLETED' THEN 1 END) as completed_sagas,
        COUNT(CASE WHEN state = 'FAILED' THEN 1 END) as failed_sagas,
        COUNT(CASE WHEN state = 'COMPENSATING' THEN 1 END) as compensating_sagas,
        COUNT(CASE WHEN state NOT IN ('COMPLETED', 'FAILED', 'COMPENSATED') THEN 1 END) as active_sagas
    FROM saga_status;
END;
$$ LANGUAGE plpgsql;

-- Grant necessary permissions
GRANT SELECT, INSERT, UPDATE, DELETE ON saga_status TO postgres;
GRANT SELECT, INSERT, UPDATE, DELETE ON saga_events TO postgres;
GRANT SELECT ON saga_monitoring TO postgres;
GRANT EXECUTE ON FUNCTION get_saga_statistics() TO postgres;
