## boot

``` base
mvn spring-boot:run
```

## test

1.取引先データを編集（下書き）
``` bash
PARTY_ID=$(curl -X POST "http://localhost:8888/api/v1/party/tempSave" -d '{"partyId": null, "partyName": "test", "representativeName": null, "address": "住所1", "userId": "100"}' -H "Content-Type: application/json" | jq -r '.partyId')
echo ${PARTY_ID}
```

2.取引先データを編集（代表者を更新）
``` bash
echo ${PARTY_ID}
data=$(sed -e "s/##PARTY_ID##/${PARTY_ID}/g" <<EOF
{"partyId": "##PARTY_ID##", "partyName": "test", "representativeName": "代表者　太郎", "address": "住所1", "userId": "100"}
EOF
)
curl -X POST "http://localhost:8888/api/v1/party/tempSave" -d "${data}" -H "Content-Type: application/json"
```

3.申請
```bash
echo ${PARTY_ID}
curl -X POST "http://localhost:8888/api/v1/party/register" -d "${data}" -H "Content-Type: application/json"
```
