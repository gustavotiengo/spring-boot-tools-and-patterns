import http from 'k6/http';

import { Rate } from 'k6/metrics';
import { check } from 'k6';

const failRate = new Rate('failed_requests');

export const options = {
    vus: 10,
    stages: [
        { duration: "20s", target: 20 },
        { duration: "30s", target: 50 },
        { duration: "30s", target: 50 },
        { duration: "20s", target: 20 }
  ],
  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<300','p(99)<700']
  },
};

export default function () {
  let res = http.get('http://app:8080/users', {headers: {Accepts: "application/json"}});
  check(res, { 'http response status code is 200': res.status === 200 });
  failRate.add(res.status !== 200);
}