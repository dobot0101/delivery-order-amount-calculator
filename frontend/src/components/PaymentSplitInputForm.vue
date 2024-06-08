<template>
  <div class="container">
    <h3 class="my-3">주문 정보 입력</h3>
    <!-- 정산 요청 입력 -->
    <div class="row d-flex justify-content-center">
      <div class="col">
        <form @submit.prevent="handleSubmit" class="mb-3">
          <div v-for="(order, index) in personOrders" :key="index" class="mb-2">
            <div class="form-group">
              <input type="text" v-model="order.name" placeholder="이름" class="form-control" />
            </div>
            <div class="form-group">
              <input type="number" v-model="order.amount" placeholder="주문금액" class="form-control" />
            </div>
            <button type="button" @click="handleRemoveLastOrder(index)" class="btn btn-outline-danger mb-2">삭제</button>
          </div>
          <div class="form-group">
            <input type="number" v-model="deliveryFee" placeholder="배달비" class="form-control" />
          </div>
          <div class="form-group">
            <input type="number" v-model="discountAmount" placeholder="할인금액" class="form-control" />
          </div>
          <button type="button" @click="handleAddOrder" class="btn btn-secondary">주문추가</button>
          <button type="submit" class="btn btn-primary">정산하기</button>
        </form>
      </div>
    </div>

    <!-- 정산 결과 표시 -->
    <div class="row d-flex justify-content-center">
      <div class="col">
        <div v-if="responseBody">
          <h3 class="my-3">정산 결과</h3>
          <div v-for="(order) in responseBody.personOrders" class="card mb-3">
            <div class="card-body">
              <h5 class="card-title">{{ order.name }}</h5>
              <p class="card-text">주문금액: {{ order.orderAmount }}</p>
              <p class="card-text">추가금액: {{ order.adjustedAmount }}</p>
              <p class="card-text">받을금액: {{ order.paymentAmount }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      personOrders: [{ name: '이도현', amount: '10000' }, { name: '송슬기', amount: '20000' }],
      deliveryFee: '2000',
      discountAmount: '3000',
      responseBody: null
    };
  },
  methods: {
    handleAddOrder() {
      this.personOrders.push({ name: '', amount: '' });
    },
    handleRemoveLastOrder(index) {
      this.personOrders.splice(index, 1);
    },
    async handleSubmit() {
      const payload = { personOrders: this.personOrders, deliveryFee: this.deliveryFee, discountAmount: this.discountAmount };
      const response = await fetch('http://localhost:8080/split-payment', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });
      if (response.ok) {
        const responseBody = await response.json();
        console.log('API 요청 성공', responseBody);
        this.responseBody = responseBody;
      } else {
        console.error('API 요청 실패');
      }
    },
  },
};
</script>
