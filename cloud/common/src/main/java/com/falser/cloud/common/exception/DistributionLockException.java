package com.falser.cloud.common.exception;

/**
 * 分布锁异常
 *
 * @author falser
 * @date 2021/06/17
 */
public class DistributionLockException extends BaseException{
    public DistributionLockException(String module, String code, Object[] args, String defaultMessage) {
        super(module, code, args, defaultMessage);
    }

    public DistributionLockException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public DistributionLockException(String module, String defaultMessage) {
        super(module, defaultMessage);
    }

    public DistributionLockException(String code, Object[] args) {
        super(code, args);
    }

    public DistributionLockException(String defaultMessage) {
        super(defaultMessage);
    }
}
