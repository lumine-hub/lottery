package com.xlm.trigger.api;

import com.xlm.trigger.api.dto.RaffleAwardListRequestDTO;
import com.xlm.trigger.api.dto.RaffleAwardListResponseDTO;
import com.xlm.trigger.api.dto.RaffleRequestDTO;
import com.xlm.trigger.api.dto.RaffleResponseDTO;
import com.xlm.types.model.Response;

import java.util.List;

/**
 * @author xlm
 * 2024/7/24 下午5:07
 * 抽奖服务接口
 */
public interface IRaffleService {

    /**
     * 策略装配接口
     *
     * @param strategyId 策略ID
     * @return 装配结果
     */
    Response<Boolean> strategyArmory(Long strategyId);

    /**
     * 查询抽奖奖品列表配置
     *
     * @param requestDTO 抽奖奖品列表查询请求参数
     * @return 奖品列表数据
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO requestDTO);

    /**
     * 随机抽奖接口
     *
     * @param requestDTO 请求参数
     * @return 抽奖结果
     */
    Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO);
}
