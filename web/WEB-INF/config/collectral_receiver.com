server {
	listen 80;
	server_name receiver.collectral.com;
	
	location /collectral/connect {
             proxy_set_header Host $host;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_pass http://10.20.0.18:8080/collectral/connect;
        }

        location / {
	     return 301 https://receiver.collectral.com$request_uri;
        }
}


server {
        listen 443 ssl;
        server_name receiver.collectral.com;

        ssl on;
        ssl_certificate /etc/ssldemo/fullchain.pem; 
        ssl_certificate_key /etc/ssldemo/privkey.pem; 


	location /connect {
             proxy_set_header Host $host;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_pass http://10.20.0.18:8080/collectral/connect;
        }

        location /collectral {
             proxy_set_header Host $host;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_pass http://10.20.0.18:8080/collectral;
        }

        location / {
             proxy_set_header Host $host;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_pass http://10.20.0.18:8080/collectral;
        }

}
