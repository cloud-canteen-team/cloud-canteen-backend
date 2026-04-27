package com.canteen.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class User {
    private Long id;
    private String studentId;
    private String password;
    // 🌟 新增的两个核心字段
    private String campusCardNo;
    private BigDecimal balance;
    private LocalDateTime createTime;

    // --- Getter 和 Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCampusCardNo() { return campusCardNo; }
    public void setCampusCardNo(String campusCardNo) { this.campusCardNo = campusCardNo; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}