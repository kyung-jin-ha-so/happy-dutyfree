package com.its.happy.service;

import com.its.happy.repository.MemberRepository;
import com.its.happy.repository.OrderRepository;
import com.its.happy.repository.PassportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;


}
